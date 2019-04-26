/****************************************************************************************
 * purpose : Model for User data.
 *
 *@author Ansar
 *@version 1.8
 *@since 22/4/2019
 ****************************************************************************************/
package com.bridgelabz.fundoo.note.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name="user_details")
@ToString
public class User implements Serializable
{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private long userId;

//	@Column(name="verification")
	boolean varified;

//	@Column(name="name")
//	@NotEmpty(message = "please provide your name")
	private String name;

//	@Column(name="email", nullable=false)
//	@Email(message = "Please provide a valid e-mail")
//	@NotEmpty(message="Please provide valid email")
	private String email;

//	@Column(name="register_Stamp")
	private LocalDateTime account_registered;

//	@Column(name="ac_update_Stamp")
	private LocalDateTime account_update;


//	@Column(name="mobileNumber")
//	@Pattern(regexp="[0-9]{10}",message = "provide valid mobile number")
	private String mobileNumber;

//	@NotEmpty(message="Please provide password")
//	@Column(name="password")
	private String password;
	private String profileImage;
	
	@ManyToMany(mappedBy="collabedUser")
	@JsonIgnore
	private List<Note> collabNotes;
	

}
