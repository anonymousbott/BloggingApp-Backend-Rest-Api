package com.smk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smk.blog.payload.UserDto;
import com.smk.blog.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/")
	public  ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		
		System.out.println("--------controller-------------");
		
		UserDto createdUser = userServiceImpl.createUser(userDto);
		
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
		
	}

}
