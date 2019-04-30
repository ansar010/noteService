package com.bridgelabz.fundoo.response;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Response
{
	private String statusMessage;	
	private int statusCode;
	
}