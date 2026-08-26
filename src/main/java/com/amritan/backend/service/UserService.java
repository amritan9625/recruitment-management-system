package com.amritan.backend.service;


import com.amritan.backend.dto.CreateUserRequest;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.UpdateUserRequest;
import com.amritan.backend.dto.UserResponseDto;

public interface UserService {

	UserResponseDto createUser(CreateUserRequest request);
	
	PageResponse<UserResponseDto> getAllUsers(int pageNo, int pageSize,
								String sortBy, String sortDir);
	
	UserResponseDto getUserById(Long id);
	
	UserResponseDto updateUser(Long id, UpdateUserRequest request);
	
	void deleteUser(Long id);
	
	PageResponse<UserResponseDto> getUsersByName(String keyword, int pageNo, int pageSize );
	
	
	PageResponse<UserResponseDto> getUsersByEmail(String keyword, int pageNo, int pageSize );
}
