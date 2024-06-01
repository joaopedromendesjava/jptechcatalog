package com.jptech.dscatalogbackend.resources.exceptions;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ForbiddenHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		StandardError error = new StandardError();
		
		if(!response.isCommitted()) {
			
			error.setTimestamp(Instant.now());
			error.setStatus(HttpStatus.FORBIDDEN.value());
			error.setError("Forbidden");
			error.setMessage("Access Denied");
			error.setPath(request.getRequestURI());
			
			ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            response.setStatus(error.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(error));
		}
	}
}
