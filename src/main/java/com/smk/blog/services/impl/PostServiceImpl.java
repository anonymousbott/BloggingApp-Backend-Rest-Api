package com.smk.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smk.blog.entity.Category;
import com.smk.blog.entity.Post;
import com.smk.blog.entity.User;
import com.smk.blog.payload.PostDto;
import com.smk.blog.payload.PostResponse;
import com.smk.blog.repositories.CategoryRepository;
import com.smk.blog.repositories.PostRepository;
import com.smk.blog.repositories.UserRepository;
import com.smk.blog.services.PostService;
import com.smk.blog.exceptions.ResourceNotFoundException;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		Post post = modelMapper.map(postDto, Post.class);

		post.setImage("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);

		Post savedPost = postRepository.save(post);

		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postById = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		postById.setContent(postDto.getContent());
		postById.setImage(postDto.getImage());
		postById.setTitle(postDto.getTitle());
		Post savedPost = postRepository.save(postById);
		return modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post postById = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "postId", postId));
		postRepository.delete(postById);

	}

	@Override
	public PostDto getPost(Integer postId) {
		Post postById = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		return modelMapper.map(postById, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = postRepository.findAll(pageable);
		List<Post> findAllPosts = pagePost.getContent();
		//List<Post> findAllPosts = postRepository.findAll(pageable)
		List<PostDto> findAllPostsDto = findAllPosts.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(findAllPostsDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category categoryById = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> postByCategory = postRepository.findByCategory(categoryById);
		List<PostDto> postDtoByCategory = postByCategory.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtoByCategory;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User userById = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
		List<Post> postByCategory = postRepository.findByUser(userById);
		List<PostDto> postDtoByCategory = postByCategory.stream().map((post) -> modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtoByCategory;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> searchPostByTitle = postRepository.searchByTitle("%"+keyword+"%");
		List<PostDto> searchPostDtoByTitle = searchPostByTitle.stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return searchPostDtoByTitle;
	}

}
