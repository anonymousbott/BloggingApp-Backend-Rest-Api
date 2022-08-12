package com.smk.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smk.blog.payload.ApiResponse;
import com.smk.blog.payload.UserDto;
import com.smk.blog.services.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

		System.out.println("--------controller-------------");

		UserDto createdUser = userServiceImpl.createUser(userDto);

		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);

	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updatedUser = userServiceImpl.updateUser(userDto, userId);

		return ResponseEntity.ok(updatedUser);

	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
		userServiceImpl.deleteUser(userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", "true"), HttpStatus.OK);

	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> alluser = userServiceImpl.getAlluser();
		return ResponseEntity.ok(alluser);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
		UserDto userById = userServiceImpl.getUserById(userId);
		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);
	}

}
