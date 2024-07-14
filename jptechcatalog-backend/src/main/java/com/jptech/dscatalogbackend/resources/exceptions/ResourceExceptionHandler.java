package com.jptech.dscatalogbackend.resources.exceptions;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jptech.dscatalogbackend.services.exceptions.DatabaseException;
import com.jptech.dscatalogbackend.services.exceptions.ResourceNotFoundException;
import com.jptech.dscatalogbackend.services.exceptions.ValidationError;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionHandler.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest req){
		
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Resource Not Founds");
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
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ValidationError err = new ValidationError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value()); //entity nao foi possivel ser validada
		err.setError("Validation exception");
		err.setMessage(ex.getMessage());
		err.setPath(request.getContextPath());
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
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
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<StandardError> authenticate(AuthenticationException e, HttpServletRequest req){
		
		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(httpStatus.value());
		err.setError("Error in Authentication");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
		log(e);
		return ResponseEntity.status(httpStatus).body(err);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus statusCode, WebRequest request) {
				
		StandardError err = new StandardError();
		
		err.setTimestamp(Instant.now());
		err.setStatus(statusCode.value());
		err.setError("Error");
		err.setMessage(ex.getMessage());
		err.setPath(request.getContextPath());
	
		return ResponseEntity.status(err.getStatus()).body(err);
		
	}
	
	private void log(Throwable exception) { // Método para aparecer o log do erro no console
	        logger.error("error message {}. Details:", exception.getMessage(), exception);
	    }
}




