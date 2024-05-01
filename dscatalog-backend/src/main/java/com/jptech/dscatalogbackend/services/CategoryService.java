package com.jptech.dscatalogbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jptech.dscatalogbackend.dto.CategoryDTO;
import com.jptech.dscatalogbackend.entities.Category;
import com.jptech.dscatalogbackend.repositories.CategoryRepository;
import com.jptech.dscatalogbackend.services.exceptions.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		
		List<Category> list = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = list.stream().map(c -> new CategoryDTO(c)).toList();

		return categoryDTOs;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(long id){
		
		Optional<Category> cat = categoryRepository.findById(id);
		Category category = cat.orElseThrow(() -> new EntityNotFoundException("Entity Not Found"));
		
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category category = new Category();
		
		if (dto.getName() != null && !dto.getName().isEmpty()) {
			category.setName(dto.getName());
			category = categoryRepository.save(category);
		}
		else {
			throw new EntityNotFoundException("Category is null");
		}
		
		
		return new CategoryDTO(category);
	}
}





