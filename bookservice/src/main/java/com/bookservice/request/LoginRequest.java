package com.bookservice.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank(message="AuthorName Cannot Be Blank")
	private String authorname;

	@NotBlank(message="Password Cannot Be Empty")
	private String password;

	
	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
