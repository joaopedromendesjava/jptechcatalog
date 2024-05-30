package com.jptech.dscatalogbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jptech.dscatalogbackend.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
}
