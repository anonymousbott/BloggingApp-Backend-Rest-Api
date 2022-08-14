package com.smk.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Integer id;
	@NotEmpty
	@Size(min =4 ,message="name must be minimum 4 characters" )
	private String name;
	@Email
	private String email;
	@NotEmpty
	@Size(min = 5, max = 10, message ="password should be minimum 5 characters and maximum 10 characters")
	private String password;
	@NotEmpty
	@Size(min =10, max =50 , message ="aboute should be minimun 10 and maximum 50 characters")
	private String about;
	

}
