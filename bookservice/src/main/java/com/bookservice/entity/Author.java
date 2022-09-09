package com.bookservice.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(	name = "authors", 
uniqueConstraints = { 
	@UniqueConstraint(columnNames = "id")
})
public class Author {//not a spring bean
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}
	public Author(Long id, @NotBlank(message = "authorname cannot be blank#######") String authorname,
			@NotBlank(message = "author email cannot be blank#######") String authoremail,
			@NotBlank(message = "password cannot be blank#######") @Size(min = 1, max = 120) String password,
			boolean loginstatus) {
		super();
		this.id = id;
		this.authorname = authorname;
		this.authoremail = authoremail;
		this.password = password;
		this.loginstatus = loginstatus;
		
	}
	public Author() {
		super();
	}
	public void setId(Long i) {
		this.id = i;
	}
	@NotBlank(message = "Author Name cannot be blank")
	private String authorname;
	@NotBlank(message = "Author Email cannot be blank")
	private String authoremail;
	
	@NotBlank(message = "Password cannot be blank")
	@Size(min=8,max = 12)
	private String password;
	
	public boolean isLoginstatus() {
		return loginstatus;
	}
	public void setLoginstatus(boolean loginstatus) {
		this.loginstatus = loginstatus;
	}
	private boolean loginstatus=false;

	public String getAuthorname() {
		return authorname;
	}
	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	public String getAuthoremail() {
		return authoremail;
	}
	public void setAuthoremail(String authoremail) {
		this.authoremail = authoremail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}