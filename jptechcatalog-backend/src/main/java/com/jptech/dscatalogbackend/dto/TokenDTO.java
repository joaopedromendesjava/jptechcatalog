package com.jptech.dscatalogbackend.dto;

public class TokenDTO {

	private String token;
	
	public TokenDTO(String tokenJWT) {
		this.token = tokenJWT;
	}

	public String getToken() {
		return token;
	}
}
