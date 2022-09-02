package com.bookservice.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String authorname;

	@NotBlank
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
