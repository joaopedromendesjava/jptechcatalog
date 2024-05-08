package com.jptech.dscatalogbackend.resources.exceptions;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jptech.dscatalogbackend.services.exceptions.DatabaseException;
import com.jptech.dscatalogbackend.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest req){
		
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource Not Found");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> argumentNotValid(ConstraintViolationException e, HttpServletRequest req){
	
		List<String> fields = e.getConstraintViolations().stream().map(t -> t.getMessageTemplate()).toList();
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.BAD_REQUEST.value());
		err.setError("Error in input");
		err.setMessage(Arrays.toString(fields.toArray()));
		err.setPath(req.getRequestURI());
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest req){
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(httpStatus.value());
		err.setError("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
	
		return ResponseEntity.status(httpStatus).body(err);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
				
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(statusCode.value());
		err.setError("Error");
		err.setMessage(ex.getMessage());
		err.setPath(request.getContextPath());
	
		return ResponseEntity.status(err.getStatus()).body(err);
		
	}
}




