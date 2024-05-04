package com.jptech.dscatalogbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jptech.dscatalogbackend.dto.ProductDTO;
import com.jptech.dscatalogbackend.entities.Product;
import com.jptech.dscatalogbackend.repositories.ProductRepository;
import com.jptech.dscatalogbackend.services.exceptions.DatabaseException;
import com.jptech.dscatalogbackend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		
		Page<Product> list = productRepository.findAll(pageRequest);
		Page<ProductDTO> productDTOs = list.map(c -> new ProductDTO(c,c.getCategories()));

		return productDTOs;
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(long id){
		
		Optional<Product> cat = productRepository.findById(id);
		Product product = cat.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		
		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO insert(@Valid ProductDTO dto) {
		
		 Product product = new Product();
		//product.setName(dto.getName());
		product = productRepository.save(product);
		
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		
		try {
			@SuppressWarnings("deprecation")
			Product productEntity = productRepository.getOne(id);
			//productEntity.setName(dto.getName());
			productEntity = productRepository.saveAndFlush(productEntity);
			return new ProductDTO(productEntity);
			
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			if (!productRepository.existsById(id)) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}else {
				productRepository.deleteById(id);
			}
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation: " + id);
		}
	}
}





