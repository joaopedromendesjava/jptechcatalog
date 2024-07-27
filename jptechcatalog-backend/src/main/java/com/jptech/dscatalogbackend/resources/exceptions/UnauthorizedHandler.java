package com.jptech.dscatalogbackend.resources.exceptions;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class UnauthorizedHandler implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		StandardError error = new StandardError();
		
		if (!response.isCommitted()) {
			
			error.setTimestamp(Instant.now());
			error.setStatus(HttpStatus.UNAUTHORIZED.value());
			error.setError("Unauthorized");
			error.setMessage("Log in to the system to try to access the requested resource");
			error.setPath(request.getRequestURI());
			
			ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            response.setStatus(error.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(error));
		}
	}

}


