package com.bridgelabz.fundoo.note.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="note_details")
@AllArgsConstructor
//@ToString
public class Note implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private long id;	 
	
	@Column(name="title")
	private String title;
	
	@Column(name="description",length=2000)
	private String description;
	
	private LocalDateTime createStamp;
	
	private LocalDateTime updateStamp;
	
	private LocalDateTime deleteStamp;
	
	
	private Timestamp remainder;
	
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isArchive;
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isPin;
	
	@JsonProperty
	@Column(columnDefinition="tinyint(1) default 0 not null")
	private boolean isTrash;
	
	private String color;
	
	@Column(columnDefinition="varchar(500)")
	private String image;
	
	private long userId;

//	@ManyToMany
//	@JoinTable(
//			name="collabNote_details",
//			joinColumns=@JoinColumn(name="collabednote_id",referencedColumnName="id"),
//			inverseJoinColumns=@JoinColumn(name="collabeduser_id",referencedColumnName="userId"))
//	private Set<User> collabedUser;
	
}
