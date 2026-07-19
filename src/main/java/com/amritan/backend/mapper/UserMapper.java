package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.UserDto;
import com.amritan.backend.entity.User;

@Component
public class UserMapper {
	
	private UserMapper() {}

	public static User mapToEntity(UserDto dto) {
		User user = new User();
		
		user.setId(dto.getId());
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		
		return user;
	}
	
	public static UserDto mapToDto(User user) {
		UserDto userDto = new UserDto();
		
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhone(user.getPhone());
		
		if(user.getRole() != null) {
			userDto.setRoleId(user.getRole().getId());
		}
		
		return userDto;
	}
}
