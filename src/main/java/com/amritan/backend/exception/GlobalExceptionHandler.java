package com.amritan.backend.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.amritan.backend.dto.ApiResponse;
import org.springframework.security.access.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException ex) {
		
		ApiResponse<Void> response = new ApiResponse<>();
		
		response.setSuccess(false);
		response.setMessage(ex.getMessage());
		response.setData(null);
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
		ApiResponse<Void> response = new ApiResponse<>();
		
		response.setSuccess(false);
        response.setMessage("Something went wrong. Please try again later.");
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
		
		
	    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>> > handleValidationErrors(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult().getFieldErrors()
	            .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

	    ApiResponse<Map<String, String>> response = new ApiResponse<>();
	    
	    response.setSuccess(false);
		response.setMessage("Validation failed");
		response.setData(errors);
		response.setTimestamp(LocalDateTime.now());
	    
	    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
	    ApiResponse<Void> response = new ApiResponse<>();

	    response.setSuccess(false);
	    response.setMessage("You do not have permission to access this resource");
	    response.setData(null);
	    response.setTimestamp(LocalDateTime.now());

	    return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse<Void>> handleDuplicateResource(DuplicateResourceException ex) {

	    ApiResponse<Void> response = new ApiResponse<>();

	    response.setSuccess(false);
	    response.setMessage(ex.getMessage());
	    response.setData(null);
	    response.setTimestamp(LocalDateTime.now());

	    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	
}