package com.smk.blog.exceptions;

public class UserNameNullOrSecurityContextNotNullException extends RuntimeException {
	
	public UserNameNullOrSecurityContextNotNullException() {
		super("user name is null or security context is not null");
	}

}
