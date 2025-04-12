package com.project.services;

import com.project.dtos.CategoryDTO;
import com.project.dtos.CategoryResponse;

import jakarta.validation.Valid;

public interface CategoryService {
	CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize);
	CategoryDTO createCategory(CategoryDTO categ);
	CategoryDTO deleteCategory(Long id);
	CategoryDTO updateCategory(Long id, @Valid CategoryDTO categ);
	
}
