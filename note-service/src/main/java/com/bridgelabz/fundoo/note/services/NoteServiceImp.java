/****************************************************************************************
 * purpose : .
 *
 *@author Ansar
 *@version 1.2
 *@since 18/12/2018
 ****************************************************************************************/
package com.bridgelabz.fundoo.note.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
//import com.bridgelabz.fundoo.note.dao.IUserRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.model.User;
//import com.bridgelabz.fundoo.note.model.User;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("noteService")
@PropertySource("classpath:message.properties")
public class NoteServiceImp implements INoteService
{
	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private UserToken userToken;

	@Autowired
	private ModelMapper modelMapper;
	
//	@Autowired
//	private IUserRepository userRepository;
//	
//	@Autowired
//	private RestTemplate restTemplate;

	private final Path fileLocation = Paths.get("/home/admin1/FundooFile");
//		private final Path fileLocation = Paths.get("G:\\FundooFile");

	@Override
	public Response createNote(NoteDTO noteDTO, String token) 
	{

		log.info("user note->"+noteDTO.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));
		//transfer DTO data into Model
		Note note = modelMapper.map(noteDTO, Note.class);
		
		note.setUserId(userId);
		note.setCreateStamp(LocalDateTime.now());
		noteRepository.save(note);
	
		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteCreate.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response updateNote(Note note, String token) 
	{
		log.info("user note->"+note.toString());
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		//User user = userRepository.findById(userId).orElse(th)
		note.setUpdateStamp(LocalDateTime.now());

		Optional<Note> dbNote = noteRepository.findById(note.getId());
		
		long dbUserId = dbNote.get().getUserId();

		if(dbUserId==userId&&dbNote.isPresent())
		{
			noteRepository.save(note);
		
			Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdate.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.noteUpdateError.errorMsg"),
				Integer.parseInt(environment.getProperty("status.noteUpdateError.errorcode")));
		return response;	
	}

	@Override
	public List<Note> getAllNoteLists(String token, String isArchive, String isTrash) {

		log.info("Token->"+token);
		log.info("isArchive->"+isArchive);
		log.info("isTrash->"+isTrash);

		long userId = userToken.tokenVerify(token);
		
////		Optional<User> dbUser = userRepository.findById(userId);
////		List<Note> collabedNotes = dbUser.get().getCollabedNotes().stream().collect(Collectors.toList());
		
		Optional<List<Note>> list_of_notes = noteRepository.findAllById(userId, Boolean.valueOf(isArchive),Boolean.valueOf(isTrash));
		
		return list_of_notes.get();
	}


	@Override
	public Response deleteForever(long noteId, String token)
	{
		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);

		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);

		log.info(String.valueOf(note.isPresent()));
		System.out.println("cehck sds"+note.isPresent());

		long dbuserId = note.get().getUserId();
		log.info("user id->"+dbuserId);
		
		boolean trash = note.get().isTrash();
		log.info("Trash->"+trash);

		if(note.isPresent()&&dbuserId==userId&&note.get().isTrash()==true)
		{
			log.info("user validation done");

			noteRepository.delete(note.get());
			
			Response response = StatusHelper.statusInfo(environment.getProperty("status.deleteForever.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.deleteForever.errorMsg"),
				Integer.parseInt(environment.getProperty("status.deleteForever.errorCode")));
		return response;
	}

	@Override
	public Response trashStatus(long noteId, String token) 
	{
		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isTrash()==true)
			{
				note.get().setTrash(false);
				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.restore.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setTrash(true);
			}
			noteRepository.save(note.get());
		}
	
		Response response = StatusHelper.statusInfo(environment.getProperty("status.moveTrash.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response pinStatus(long noteId, String token)
	{
		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isPin()==true)
			{
				note.get().setPin(false);

				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.unpin.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setPin(true);
			}
			noteRepository.save(note.get());
		}
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.pinned.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	@Override
	public Response archiveStatus(long noteId, String token) 
	{
		log.info("Note id->"+noteId);
		log.info("Token->"+token);

		long userId = userToken.tokenVerify(token);
		log.info(Long.toString(userId));

		Optional<Note> note = noteRepository.findById(noteId);
		long dbuserId = note.get().getUserId();

		if(dbuserId==userId)
		{
			if(note.get().isArchive()==true)
			{
				note.get().setArchive(false);

				noteRepository.save(note.get());

				Response response = StatusHelper.statusInfo(environment.getProperty("status.unarchive.successMsg"),
						Integer.parseInt(environment.getProperty("status.success.code")));

				return response;
			}
			else
			{
				note.get().setArchive(true);
			}
			noteRepository.save(note.get());
		}
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.archive.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
	}

	

	//method to store file 
	@Override
	public Response saveNoteImage(String token, MultipartFile file, Long noteId) {

		Note note = noteRepository.findById(noteId).get();

		long userId = userToken.tokenVerify(token);

		// generate random universal unique id 
		UUID uuid = UUID.randomUUID();

		String uniqueStringId = uuid.toString();

		//copying file stream in target file
		try {
			Files.copy(file.getInputStream(), fileLocation.resolve(uniqueStringId),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(note.getUserId()==userId)
		{
			note.setImage(uniqueStringId);
			noteRepository.save(note);

			Response response = StatusHelper.statusInfo(environment.getProperty("status.imageUpload.successMsg"),
					Integer.parseInt(environment.getProperty("status.success.code")));

			return response;
		}
		Response response = StatusHelper.statusInfo(environment.getProperty("status.imageUpload.errorMsg"),
				Integer.parseInt(environment.getProperty("status.error.code")));

		return response;
	}	

	@Override
	public Resource getNoteImage(Long noteId) {

		Note note = noteRepository.findById(noteId).get();

		// get image name from database
		Path imagePath = fileLocation.resolve(note.getImage());

		try {
			//creating url resource based on uri object
			Resource resource = new UrlResource(imagePath.toUri());
			if(resource.exists() || resource.isReadable())
			{
				return resource;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Response addCollab(long noteId, String userMailId, String token) {
		RestTemplate restTemplate= new RestTemplate();
		
		User user = restTemplate.getForObject("http://localhost:8081/user/userdetails/"+userMailId, User.class);
		System.out.println();
		log.info("micro user",user.toString());
//		Note note = noteRepository.findById(noteId).get();
//		
//		user.getCollabedNotes().add(note);
//		userRepository.save(user);
//		note.getCollabedUsers().add(user);
//		noteRepository.save(note);
		
		Response response = StatusHelper.statusInfo(environment.getProperty("status.addCollab.successMsg"),
				Integer.parseInt(environment.getProperty("status.success.code")));

		return response;
		}


}
