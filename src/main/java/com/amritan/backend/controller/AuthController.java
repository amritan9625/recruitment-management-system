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
import com.amritan.backend.dto.UserDto;
import com.amritan.backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(
			@Valid @RequestBody LoginRequest request){
		
		LoginResponse response = authService.login(request);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserDto>> register(
			@Valid @RequestBody RegisterRequest request){
		
		UserDto userDto = authService.register(request);
		
		ApiResponse<UserDto> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("User registered successfully");
		response.setData(userDto);
		response.setTimestamp(LocalDateTime.now());
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
