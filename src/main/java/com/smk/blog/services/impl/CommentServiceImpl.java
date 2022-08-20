package com.smk.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.blog.entity.Comment;
import com.smk.blog.entity.Post;
import com.smk.blog.exceptions.ResourceNotFoundException;
import com.smk.blog.payload.CommentDto;
import com.smk.blog.repositories.CommentRepository;
import com.smk.blog.repositories.PostRepository;
import com.smk.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto,Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "postId", postId));
		Comment comment = modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = commentRepository.save(comment);
		return modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "commentId", commentId));
		commentRepository.delete(comment);

	}

}
