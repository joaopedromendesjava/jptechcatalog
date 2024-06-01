package com.jptech.dscatalogbackend.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jptech.dscatalogbackend.config.TokenService;
import com.jptech.dscatalogbackend.dto.AuthLoginDTO;
import com.jptech.dscatalogbackend.dto.TokenDTO;
import com.jptech.dscatalogbackend.entities.User;

@RestController
@RequestMapping("/login")
public class Authentication {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> Login(@RequestBody @Valid AuthLoginDTO user){
    		
	        UsernamePasswordAuthenticationToken authenticationToken = 
	        			new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
	        var authentication = manager.authenticate(authenticationToken);
	        
	        String tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
	        
	     return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}



