package com.bridgelabz.fundoo.note.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.services.INoteService;

@RunWith(SpringJUnit4ClassRunner.class)
public class NoteControllerTest {

	@Mock
	private INoteService noteSerive;
//	
	@InjectMocks
	private NoteController noteController;
//	
//	@Mock
//	private BindingResult bindingResult;
//	
//	@Mock
//	private NoteDTO noteDTO;
//	
//	@Mock
//	private String token;
	
	@Test
	public void createNote() {
//		fail("Not yet implemented");
//		when(noteController.createNote(noteDTO, bindingResult, token)).thenReturn(value)
		assertEquals(noteController.hello().getStatusCode(), HttpStatus.CREATED);
	}

}
