package com.smk.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smk.blog.entity.Category;
import com.smk.blog.exceptions.ResourceNotFoundException;
import com.smk.blog.payload.CategoryDto;
import com.smk.blog.repositories.CategoryRepository;
import com.smk.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category savedCategory = categoryRepository.save(modelMapper.map(categoryDto, Category.class));
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category categoryById = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		if (categoryDto.getCategoryTitle() != null)
			categoryById.setCategoryTitle(categoryDto.getCategoryTitle());
		if (categoryDto.getCategoryDescription() != null)
			categoryById.setCategoryDescription(categoryDto.getCategoryDescription());
		;
		Category savedCategory = categoryRepository.save(categoryById);
		return modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category categoryById = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(categoryById);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category categoryById = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		return modelMapper.map(categoryById, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<CategoryDto> allCategories = categoryRepository.findAll().stream()
				.map((category) -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return allCategories;
	}

}
