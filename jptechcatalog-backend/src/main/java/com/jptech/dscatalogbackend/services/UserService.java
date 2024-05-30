package com.jptech.dscatalogbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jptech.dscatalogbackend.dto.RoleDTO;
import com.jptech.dscatalogbackend.dto.UserDTO;
import com.jptech.dscatalogbackend.dto.UserInsertDTO;
import com.jptech.dscatalogbackend.entities.Role;
import com.jptech.dscatalogbackend.entities.User;
import com.jptech.dscatalogbackend.repositories.RoleRepository;
import com.jptech.dscatalogbackend.repositories.UserRepository;
import com.jptech.dscatalogbackend.services.exceptions.DatabaseException;
import com.jptech.dscatalogbackend.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(PageRequest pageRequest){
		
		Page<User> list = userRepository.findAll(pageRequest);
		Page<UserDTO> userDTOs = list.map(c -> new UserDTO(c));

		return userDTOs;
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(long id){
		
		Optional<User> cat = userRepository.findById(id);
		User user = cat.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO insert(@Valid UserInsertDTO dto) {
		
		User user = new User();
		copyDtoToUser(dto,user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user = userRepository.save(user);
		
		return new UserDTO(user);
	}
	
	@Transactional
	public UserDTO update(Long id, UserDTO dto) {
		
		try {
			User userEntity = userRepository.getReferenceById(id);
			copyDtoToUser(dto,userEntity);
			userEntity = userRepository.save(userEntity);
			return new UserDTO(userEntity);
			
		}catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: " + id);
		}
	}
	public void delete(Long id) {
		try {
			if (!userRepository.existsById(id)) {
				throw new ResourceNotFoundException("Id not found: " + id);
			}else {
				userRepository.deleteById(id);
			}
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation: " + id);
		}
	}
	
	private void copyDtoToUser(UserDTO dto, User userEntity) {
		
		userEntity.setEmail(dto.getEmail());
		userEntity.setFirstName(dto.getFirstName());
		userEntity.setLastName(dto.getLastName());
		
		userEntity.getRoles().clear();
		for (RoleDTO roleDTO : dto.getRoles()) {
			Role role = roleRepository.getReferenceById(roleDTO.getId());
			userEntity.getRoles().add(role);
		}
	}
}





