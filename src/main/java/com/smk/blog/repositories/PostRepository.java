package com.smk.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smk.blog.entity.Category;
import com.smk.blog.entity.Post;
import com.smk.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	public abstract List<Post> findByCategory(Category category);
	
	public abstract List<Post> findByUser(User user);
	
	@Query("select p from Post p where p.title like :key")
	public List<Post> searchByTitle(@Param("key")String title);

}
