package com.jptech.dscatalogbackend.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jptech.dscatalogbackend.entities.Category;
import com.jptech.dscatalogbackend.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAlll(){
	
		List<Category> listCateg = categoryService.findAll();
		
		return ResponseEntity.ok().body(listCateg);
	}
}
