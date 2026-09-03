package com.amritan.backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.ApiResponse;
import com.amritan.backend.dto.LoginRequest;
import com.amritan.backend.dto.LoginResponse;
import com.amritan.backend.dto.RegisterRequest;
import com.amritan.backend.dto.UserResponseDto;
import com.amritan.backend.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication APIs",
        description = "APIs for user authentication and registration"
)
@SecurityRequirements
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
	@Operation(
	        summary = "Login user",
	        description = "Authenticates a user and returns a JWT token",
	        	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        					description = "User login credentials",
	        					required = true
	                )
	)
	public ResponseEntity<LoginResponse> login(
			@Valid @RequestBody LoginRequest request){
		
		LoginResponse response = authService.login(request);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/register")
	@Operation(
	        summary = "Register user",
	        description = "Registers a new user in the system",
	        	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        					description = "User registration details",
	        					required = true
	                )
	)
	public ResponseEntity<ApiResponse<UserResponseDto>> register(
			@Valid @RequestBody RegisterRequest request){
		
		UserResponseDto userResponse = authService.register(request);
		
		ApiResponse<UserResponseDto> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("User registered successfully");
		response.setData(userResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
