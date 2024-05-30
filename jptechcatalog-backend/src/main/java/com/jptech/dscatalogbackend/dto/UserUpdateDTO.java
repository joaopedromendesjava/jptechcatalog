package com.jptech.dscatalogbackend.dto;

import java.io.Serializable;

import com.jptech.dscatalogbackend.services.validation.UserUpdateValid;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;

}
