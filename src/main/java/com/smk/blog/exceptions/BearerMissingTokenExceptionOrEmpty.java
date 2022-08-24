package com.smk.blog.exceptions;

public class BearerMissingTokenExceptionOrEmpty extends RuntimeException {
	
	public BearerMissingTokenExceptionOrEmpty() {
		super("Bearer missing or empty token");
	}

}
