package com.amritan.backend.service;

import com.amritan.backend.dto.LoginRequest;
import com.amritan.backend.dto.LoginResponse;
import com.amritan.backend.dto.RegisterRequest;
import com.amritan.backend.dto.UserResponseDto;

public interface AuthService {

	LoginResponse login(LoginRequest request);
	
	UserResponseDto register(RegisterRequest request);
}
