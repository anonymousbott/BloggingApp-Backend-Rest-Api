package com.smk.blog.services;

import java.util.List;

import com.smk.blog.payload.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto updateUser(UserDto userDto,Integer id);
	
	void deleteUser(Integer id);
	
	UserDto getUserById(Integer id);
	
	List<UserDto> getAlluser();

}
