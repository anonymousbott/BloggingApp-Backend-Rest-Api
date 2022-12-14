package com.smk.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smk.blog.config.AppConstants;
import com.smk.blog.payload.ApiResponse;
import com.smk.blog.payload.PostDto;
import com.smk.blog.payload.PostResponse;
import com.smk.blog.repositories.PostRepository;
import com.smk.blog.services.FileService;
import com.smk.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDto createdPost = postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable Integer categoryId) {
		List<PostDto> postByCategory = postService.getPostByCategory(categoryId);

		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable Integer userId) {
		List<PostDto> postByUser = postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> findPostById(@PathVariable Integer postId) {

		PostDto post = postService.getPost(postId);

		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	@GetMapping("/post")
	public ResponseEntity<PostResponse> findAllPosts(
			@RequestParam(name = "pageNumber", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_BY) String sortBy,
			@RequestParam(name = "sortDir", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir) {
		PostResponse allPosts = postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(allPosts, HttpStatus.OK);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("post deleted successfully", "true"), HttpStatus.OK);
	}

	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPost = postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}

	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
		List<PostDto> searchPosts = postService.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(searchPosts, HttpStatus.OK);
	}

	@GetMapping("/post/image/{postId}")
	public ResponseEntity<PostDto> uploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile image)
			throws IOException {
		PostDto postDto = postService.getPost(postId);

		String uploadedImageName = fileService.uploadImage(path, image);

		postDto.setImage(uploadedImageName);

		PostDto updatedPost = postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

	}

	@GetMapping(value = "/post/viewimage/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
