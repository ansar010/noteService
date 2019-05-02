package com.bridgelabz.fundoo.note.ServiceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bridgelabz.fundoo.note.dao.INoteRepository;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.services.NoteServiceImp;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.StatusHelper;
import com.bridgelabz.fundoo.util.UserToken;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(PowerMockRunner.class)
public class ServiceTest {

	@Mock
	private INoteRepository noterepository;
	
	@InjectMocks
	private NoteServiceImp noteService;
	
	@Mock
	private UserToken userToken;
	
	@Mock
	private ModelMapper modelMapper;
	
	
//	@Before
//	public void setUp() throws Exception {
//	}

	@Test
	public void testGetAllNote() {	
		List<Note> noteList = new ArrayList<>();
		noteList.add( new Note(1, "first Note", "created", LocalDateTime.now(), null, null, null, false, false, false, null, null, 1));
		noteList.add( new Note(2, "second Note", "created",LocalDateTime.now(), null, null, null, false, false, false, null, null, 1));
		noteList.add( new Note(3, "third Note", "created", LocalDateTime.now(), null, null, null, false, false, false, null, null, 1));

		when(userToken.tokenVerify(anyString())).thenReturn(1l);

		when(noterepository.findAllByUserId(anyLong(), anyBoolean(),anyBoolean())).thenReturn(noteList);
		
		List<Note> result = noteService.getAllNoteLists("token", "false", "false");
		
		assertEquals(3, result.size());
	}
	

	@Test
	public void testCreateNote() {	

		NoteDTO noteDto = new NoteDTO("createTest", "testing creaete", null, false);
		Note note= new Note(1, "second Note", "created",LocalDateTime.now(), null, null, null, false, false, false, null, null, 1);
		Response response = new Response("success", 200);
		
		
		when(userToken.tokenVerify(anyString())).thenReturn(1l);

		when(modelMapper.map(noteDto, Note.class)).thenReturn(note);
		
//		when(statushelper.statusInfo(anyString(), (int) anyLong())).thenReturn(response);

		
		when(noterepository.save(note)).thenReturn(note);
		

		Response result = noteService.createNote(noteDto, "token");	
		
		assertEquals(response.getStatusMessage(), result.getStatusMessage());
	}

}
