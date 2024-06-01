package com.jptech.dscatalogbackend.dto;

import javax.validation.constraints.Email;

import org.springframework.stereotype.Component;

@Component
public class AuthLoginDTO {

	@Email(message = "Email deve ser válido")
	private String email;
	
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
}
