package com.jptech.dscatalogbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jptech.dscatalogbackend.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
}
