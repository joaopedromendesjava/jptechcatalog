package com.jptech.dscatalogbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jptech.dscatalogbackend.dto.CategoryDTO;
import com.jptech.dscatalogbackend.entities.Category;
import com.jptech.dscatalogbackend.repositories.CategoryRepository;
import com.jptech.dscatalogbackend.services.exceptions.DatabaseException;
import com.jptech.dscatalogbackend.services.exceptions.ResourceNotFoundException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Category> list = categoryRepository.findAll(pageRequest);
		Page<CategoryDTO> categoryDTOs = list.map(c -> new CategoryDTO(c));

		return categoryDTOs;
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(long id){
		
		Optional<Category> cat = categoryRepository.findById(id);
		Category category = cat.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(@Valid CategoryDTO dto) {
		Category category = new Category();
		
		category.setName(dto.getName());
		category = categoryRepository.save(category);
		
		return new CategoryDTO(category);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		
		try {
			@SuppressWarnings("deprecation")
			Category categoryEntity = categoryRepository.getOne(id);
			categoryEntity.setName(dto.getName());
			categoryEntity = categoryRepository.saveAndFlush(categoryEntity);
			return new CategoryDTO(categoryEntity);
			
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			if (!categoryRepository.existsById(id)) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}else {
				categoryRepository.deleteById(id);
			}
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation: " + id);
		}
	}
}





