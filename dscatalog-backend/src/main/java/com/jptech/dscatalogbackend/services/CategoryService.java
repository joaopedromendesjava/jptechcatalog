package com.jptech.dscatalogbackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jptech.dscatalogbackend.dto.CategoryDTO;
import com.jptech.dscatalogbackend.entities.Category;
import com.jptech.dscatalogbackend.repositories.CategoryRepository;

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
}
