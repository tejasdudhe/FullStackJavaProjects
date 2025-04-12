package com.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.project.dtos.CategoryDTO;
import com.project.dtos.CategoryResponse;
import com.project.model.Category;
import com.project.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	private CategoryService categService;

//	public CategoryController(CategoryService categService) {
//		super();
//		this.categService = categService;
//	}

	

	
	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getCategories(
			@RequestParam(name = "pageNumber")Integer pageNumber,
			@RequestParam(name = "pageSize")Integer pageSize) {
		CategoryResponse categResponse = categService.getAllCategories(pageNumber,pageSize);
		return new ResponseEntity<>(categResponse, HttpStatus.OK);
	}

	@PostMapping("/admin/categories")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categ) {

		CategoryDTO categDto = categService.createCategory(categ);
//			return ResponseEntity.status(HttpStatus.OK).body(Status);
//		return ResponseEntity.ok(categDto);
		return new ResponseEntity<>(categDto,HttpStatus.CREATED);

	}

	@DeleteMapping("/admin/categories/{id}")
	public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {

		CategoryDTO categDto = categService.deleteCategory(id);
		return new ResponseEntity<>(categDto, HttpStatus.OK);
	}

	@PutMapping("/admin/categories/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryDTO categDto) {

		CategoryDTO Status = categService.updateCategory(id, categDto);
		return new ResponseEntity<>(Status, HttpStatus.OK);

	}
}
