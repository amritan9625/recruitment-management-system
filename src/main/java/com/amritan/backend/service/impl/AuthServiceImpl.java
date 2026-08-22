package com.amritan.backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.LoginRequest;
import com.amritan.backend.dto.LoginResponse;
import com.amritan.backend.security.JwtService;
import com.amritan.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	
	@Override
	public LoginResponse login(LoginRequest request) {

	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword()
	            )
	    );

	    String token = jwtService.generateToken(request.getEmail());

	    return new LoginResponse(
	            token,
	            "Bearer"
	    );
	}

}
