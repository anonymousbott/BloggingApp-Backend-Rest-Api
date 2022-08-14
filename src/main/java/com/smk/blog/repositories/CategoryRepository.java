package com.smk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smk.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
