package com.bridgelabz.fundoo.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO
{
	private String title;
	private String description;
	private String color;
	
	@JsonProperty
	private boolean isPin;
}
