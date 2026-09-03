package com.amritan.backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.LoginRequest;
import com.amritan.backend.dto.LoginResponse;
import com.amritan.backend.dto.RegisterRequest;
import com.amritan.backend.dto.UserResponseDto;
import com.amritan.backend.entity.Role;
import com.amritan.backend.entity.User;
import com.amritan.backend.exception.DuplicateResourceException;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.UserMapper;
import com.amritan.backend.repository.RoleRepository;
import com.amritan.backend.repository.UserRepository;
import com.amritan.backend.security.JwtService;
import com.amritan.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public LoginResponse login(LoginRequest request) {
	    authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    request.getEmail(),
	                    request.getPassword() )
	    );
	    String token = jwtService.generateToken(request.getEmail());

	    return new LoginResponse(
	            token,
	            "Bearer"
	    );
	}


	@Override
	public UserResponseDto register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	        throw new DuplicateResourceException("User already exists with email: "
	                        + request.getEmail());
	    }

	    Role candidateRole = roleRepository.findByRoleName("CANDIDATE")
	            .orElseThrow(() -> new ResourceNotFoundException(
	                            "CANDIDATE role not found"));

	    User user = new User();

	    user.setName(request.getName());
	    user.setEmail(request.getEmail());
	    user.setPhone(request.getPhone());

	    user.setPassword(passwordEncoder.encode(request.getPassword()));

	    user.setRole(candidateRole);

	    User savedUser = userRepository.save(user);

	    return UserMapper.mapToDto(savedUser);
	}

}
