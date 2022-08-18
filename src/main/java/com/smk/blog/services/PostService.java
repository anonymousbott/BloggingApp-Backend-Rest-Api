package com.smk.blog.services;

import java.util.List;

import com.smk.blog.payload.PostDto;
import com.smk.blog.payload.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	void deletePost(Integer postId);
	
	PostDto getPost(Integer postId);
	
	PostResponse getAllPosts(Integer pageNumber,Integer PageSize);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);

}
