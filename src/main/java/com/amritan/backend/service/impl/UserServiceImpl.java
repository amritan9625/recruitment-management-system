package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amritan.backend.dto.UserDto;
import com.amritan.backend.entity.Role;
import com.amritan.backend.entity.User;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.UserMapper;
import com.amritan.backend.repository.RoleRepository;
import com.amritan.backend.repository.UserRepository;
import com.amritan.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	
	
	
	@Override
	public UserDto createUserDto(UserDto dto) {
		User user = UserMapper.mapToEntity(dto);
		
		Role role = roleRepository.findById(dto.getRoleId())
		        .orElseThrow(() ->
		                new ResourceNotFoundException(
		                        "Role not found with id : " + dto.getRoleId()));
		user.setRole(role);
		User savedUser = userRepository.save(user);
		
		return UserMapper.mapToDto(savedUser);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		
		return users.stream()
				.map(UserMapper::mapToDto)
				.toList();
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException(
								"User not found with id : "+id));
		
		return UserMapper.mapToDto(user);
	}

	@Override
	public UserDto updateUser(Long id, UserDto dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException(
								"User not found with id : "+id));
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		
		Role role = roleRepository.findById(dto.getRoleId())
		        .orElseThrow(() ->
		                new ResourceNotFoundException(
		                        "Role not found with id : " + dto.getRoleId()));

		user.setRole(role);
		
		User updatedUser = userRepository.save(user);
		
		return UserMapper.mapToDto(updatedUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException(
								"User not found with id : "+id));
		
		userRepository.delete(user);
		
	}

}
