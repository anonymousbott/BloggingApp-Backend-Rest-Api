package com.smk.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.smk.blog.entity.Category;
import com.smk.blog.entity.Comment;
import com.smk.blog.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	private String title;
	private String content;
	private String image;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	/*
	 * private Category category; private User user;
	 */
	
	private Set<CommentDto>comments = new HashSet<>();

}
