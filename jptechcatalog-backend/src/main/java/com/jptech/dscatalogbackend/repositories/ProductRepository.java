package com.jptech.dscatalogbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jptech.dscatalogbackend.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
}
