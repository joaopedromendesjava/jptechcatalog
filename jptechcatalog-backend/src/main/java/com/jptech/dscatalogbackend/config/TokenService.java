package com.jptech.dscatalogbackend.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jptech.dscatalogbackend.entities.User;

@Service
public class TokenService {
	
	@Value("${jwt.secret}")
    private String secret;
	
	@Value("${jwt.duration}")
    private Integer duration;

	@Value("${jwt.sender}")
    private String sender;
	
	public String generateToken(User user) { //cria token
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(sender)
                    .withSubject(user.getEmail())
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algoritmo);
        } catch (JWTCreationException e){
            throw new JWTCreationException("Error in create token", e);
        }
    }
	
	public String getSubject(String tokenJWT) { //verifica token
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer(sender)
                    .build()
                    .verify(tokenJWT)
                    .getSubject(); //retorna token
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("Token JWT invalid or expired");
        }
    }

    
	
}
