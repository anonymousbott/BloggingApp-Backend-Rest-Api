package com.smk.blog.exceptions;

public class InvalidTokenException extends RuntimeException {
	
	public InvalidTokenException() {
		super("Inavlid token");
	}

}
