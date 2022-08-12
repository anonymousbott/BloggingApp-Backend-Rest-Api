package com.smk.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.blog.entity.User;
import com.smk.blog.exceptions.ResourceNotFoundException;
import com.smk.blog.payload.UserDto;
import com.smk.blog.repositories.UserRepository;
import com.smk.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		System.out.println("in................");
		User savedUser = userRepository.save(dtoToUser(userDto));
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		User savedUser = userRepository.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAlluser() {
		List<UserDto> collect = userRepository.findAll().stream().map((user)-> userToDto(user)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = new User();
		user.setAbout(userDto.getAbout());
		user.setEmail(userDto.getEmail());
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto userToDto(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setAbout(user.getAbout());
		userDto.setEmail(user.getEmail());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setPassword(user.getPassword());
		return userDto;
		
	}

}
