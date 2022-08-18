package com.smk.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.blog.entity.Category;
import com.smk.blog.entity.Post;
import com.smk.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	public abstract List<Post> findByCategory(Category category);
	
	public abstract List<Post> findByUser(User user);

}
