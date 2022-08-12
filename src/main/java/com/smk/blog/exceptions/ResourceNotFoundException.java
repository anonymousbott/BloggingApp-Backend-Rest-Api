package com.smk.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	private String resource;
	private String field;
	private Integer value;
	
	public ResourceNotFoundException(String resource, String field, Integer value) {
		super(resource +" with field "+field + " on value="+value+" not found");
		this.resource = resource;
		this.field = field;
		this.value = value;
	}
	
	

	
}
