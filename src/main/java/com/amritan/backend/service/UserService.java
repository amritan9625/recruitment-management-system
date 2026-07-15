package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.UserDto;

public interface UserService {

	UserDto createUserDto(UserDto dto);
	
	List<UserDto> getAllUsers();
	
	UserDto getUserById(Long id);
	
	UserDto updateUser(Long id, UserDto dto);
	
	void deleteUser(Long id);
	
}
