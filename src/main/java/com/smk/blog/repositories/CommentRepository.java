package com.smk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
