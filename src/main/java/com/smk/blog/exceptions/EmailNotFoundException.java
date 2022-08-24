package com.smk.blog.exceptions;

public class EmailNotFoundException extends RuntimeException {

	private String email;
	

	public EmailNotFoundException(String email) {
		super("email with email id " + email + " not found");
		this.email = email;
	}

}
