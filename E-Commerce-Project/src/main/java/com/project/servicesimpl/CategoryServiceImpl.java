package com.project.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.project.dtos.CategoryDTO;
import com.project.dtos.CategoryResponse;
import com.project.exceptions.APIException;
import com.project.exceptions.ResourceNotFoundException;
import com.project.model.Category;
import com.project.repository.CategoryRepository;
import com.project.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize) {
		
		Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
		Page<Category> pageCateg = categRepo.findAll(pageDetails);
		List<Category> checkList = pageCateg.getContent();
		
		if(checkList.isEmpty())
			throw new APIException("There is No Category is Created yet !!!");
		
		List<CategoryDTO> dtoList = checkList.stream().map(category ->modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		CategoryResponse categResponse = new CategoryResponse();
		
		categResponse.setContents(dtoList);
		return categResponse;
	}

	@Override
	public CategoryDTO createCategory(CategoryDTO categDto) {
		
		Category categEntity = modelMapper.map(categDto, Category.class);
		Category categcheck = categRepo.findByCategName(categEntity.getCategName());
		
		if (categcheck != null) 
			throw new APIException("Category with the Name "+categcheck.getCategName()+" is already Exist !!!");
			
		
		Category categSave = categRepo.save(categEntity);
		
			return modelMapper.map(categSave,CategoryDTO.class);
	}

	@Override
	public CategoryDTO deleteCategory(Long id) {

		
		Optional<Category> opCateg = categRepo.findById(id);

		Category newCateg = opCateg
				.orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));

		categRepo.delete(newCateg);

		return modelMapper.map(newCateg,CategoryDTO.class);
		
		
//		 List<Category> categList = categRepo.findAll();
//		Category categ = categList.stream()
//		        .filter(c -> c.getCategId().equals(id))
//		        .findFirst()
//		        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));
//
//				categRepo.delete(categ);
//		        return "Category Removed Successfully....";
	}

	@Override
	public CategoryDTO updateCategory(Long id, CategoryDTO categDto) {
		
		Category categEntity = modelMapper.map(categDto, Category.class);
		Optional<Category> opCateg = categRepo.findById(id);

		Category newCateg = opCateg
				.orElseThrow(() -> new ResourceNotFoundException("Category","CategoryId",id));

//		Category newCateg = categList.stream().filter(c -> c.getCategId().equals(id)).findFirst()
//				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category Not Found"));

		newCateg.setCategName(categEntity.getCategName());
		categRepo.save(newCateg);
		
		CategoryDTO categDtoSave = modelMapper.map(newCateg, CategoryDTO.class);
		return categDtoSave;

	}
}
