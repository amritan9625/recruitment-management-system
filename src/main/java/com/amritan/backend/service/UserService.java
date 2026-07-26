package com.amritan.backend.service;


import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.UserDto;

public interface UserService {

	UserDto createUserDto(UserDto dto);
	
	PageResponse<UserDto> getAllUsers(int pageNo, int pageSize,
								String sortBy, String sortDir);
	
	UserDto getUserById(Long id);
	
	UserDto updateUser(Long id, UserDto dto);
	
	void deleteUser(Long id);
	
	PageResponse<UserDto> getUsersByName(String keyword, int pageNo, int pageSize );
	
	
	PageResponse<UserDto> getUsersByEmail(String keyword, int pageNo, int pageSize );
}
