package com.bookservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(	name = "author", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "id")
})
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Author Name cannot be blank")
	private String authorname;
	@Email(message="Please Enter a Valid Email")
	@NotBlank(message = "Author Email cannot be blank")
	private String authoremail;
	@NotBlank(message = "Password cannot be blank")
	@Size(min=8,max=120,message="Invalid Password, minimum size is 8")
	private String password;
	private boolean loginstatus;
}