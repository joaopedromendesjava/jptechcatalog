package com.jptech.dscatalogbackend.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jptech.dscatalogbackend.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@GetMapping
	public ResponseEntity<List<Category>> findAlll(){
		List<Category> listCateg = new ArrayList<>();
		
		listCateg.add(new Category(1L, "Books"));
		
		return ResponseEntity.ok().body(listCateg);
	}
}
