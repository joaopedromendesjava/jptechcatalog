package com.jptech.dscatalogbackend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jptech.dscatalogbackend.dto.CategoryDTO;
import com.jptech.dscatalogbackend.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAlll(){
	
		List<CategoryDTO> listCateg = categoryService.findAll();
		
		return ResponseEntity.ok().body(listCateg);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable(value = "id") long id){
	
		CategoryDTO dto = categoryService.findById(id);
		
		return ResponseEntity.ok().body(dto);
	}
}
