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
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ErrorResponse {
	    private boolean success;
	    private String message;
	    private LocalDateTime timestamp;
	}
	

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFound(
			ResourceNotFoundException ex) {
		
		return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage(), LocalDateTime.now())
								, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {

	    return new ResponseEntity<>(new ErrorResponse(false, ex.getMessage(), LocalDateTime.now())
	    							, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> errors = new HashMap<>();

	    ex.getBindingResult()
	            .getFieldErrors()
	            .forEach(error ->
	                    errors.put(
	                            error.getField(),
	                            error.getDefaultMessage()));

	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(
	        AccessDeniedException ex) {

	    ApiResponse<String> response = new ApiResponse<>();

	    response.setSuccess(false);
	    response.setMessage(ex.getMessage());
	    response.setData(null);
	    response.setTimestamp(LocalDateTime.now());

	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
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