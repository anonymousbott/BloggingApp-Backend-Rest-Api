package com.smk.blog.services;

import java.util.List;

import com.smk.blog.payload.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	void deleteCategory(Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	
	List<CategoryDto> getAllCategories();

}
