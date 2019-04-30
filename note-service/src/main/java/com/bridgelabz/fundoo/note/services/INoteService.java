package com.bridgelabz.fundoo.note.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.response.Response;

public interface INoteService {

	Response createNote(NoteDTO noteDTO, String token);

	Response updateNote(Note note, String token);

	Response deleteForever(long noteId, String token);

	Response trashStatus(long noteId, String token);

	Response pinStatus(long noteId, String token);

	Response archiveStatus(long noteId, String token);

	List<Note> getAllNoteLists(String token, String isArchive, String isTrash);

	Response saveNoteImage(String token, MultipartFile file, Long noteId);

	Resource getNoteImage(Long noteId);

//	Response addCollab(long noteId, String userMailId, String token);

	
}
