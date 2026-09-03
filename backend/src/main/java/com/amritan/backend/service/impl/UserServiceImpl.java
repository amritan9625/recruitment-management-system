package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.CreateUserRequest;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.UpdateUserRequest;
import com.amritan.backend.dto.UserResponseDto;
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

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserResponseDto createUser(CreateUserRequest request) {
		User user = UserMapper.mapToEntity(request);

	    user.setPassword(passwordEncoder.encode(request.getPassword()));

	    Role role = roleRepository.findById(request.getRoleId())
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Role not found with id : "+request.getRoleId()));

	    user.setRole(role);

	    User savedUser = userRepository.save(user);

	    return UserMapper.mapToDto(savedUser);
	}

	
	@Override
	public PageResponse<UserResponseDto> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<User> page = userRepository.findAll(pageable);
		
		List<UserResponseDto> content = page.getContent()
				.stream()
				.map(UserMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				, page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}
	

	@Override
	public UserResponseDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("User not found with id : "+id));
		
		return UserMapper.mapToDto(user);
	}

	@Override
	public UserResponseDto updateUser(Long id, UpdateUserRequest request) {
		User user = userRepository.findById(id)
				.orElseThrow(() ->
						new ResourceNotFoundException("User not found with id : "+id));
		
		UserMapper.updateEntity(user, request);
		
		Role role = roleRepository.findById(request.getRoleId())
		        .orElseThrow(() ->
		                new ResourceNotFoundException("Role not found with id : " + request.getRoleId()));

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


	@Override
	public PageResponse<UserResponseDto> getUsersByName(String keyword, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<User> page = userRepository.findByNameContainingIgnoreCase(
				keyword, pageable);
		
		List<UserResponseDto> content = page.getContent()
				.stream()
				.map(UserMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				, page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public PageResponse<UserResponseDto> getUsersByEmail(String keyword, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<User> page = userRepository.findByEmailContainingIgnoreCase(keyword, pageable);
		
		List<UserResponseDto> content = page.getContent()
				.stream()
				.map(UserMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				, page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

}
