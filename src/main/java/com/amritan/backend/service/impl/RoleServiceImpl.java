package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amritan.backend.dto.RoleDto;
import com.amritan.backend.entity.Role;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.RoleMapper;
import com.amritan.backend.repository.RoleRepository;
import com.amritan.backend.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

	private final RoleRepository roleRepository;
	

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		Role role = RoleMapper.mapToEntity(roleDto);	// DTO --> Entity
		Role savedRole = roleRepository.save(role);		// save into database
		
		return RoleMapper.mapToDto(savedRole);			// Entity --> DTO
	}

	@Override
	public List<RoleDto> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		
		return roles.stream()
				.map(RoleMapper::mapToDto)
				.toList();
	}

	@Override
	public RoleDto getRoleById(Long id) {
		Role role = roleRepository.findById(id)
					.orElseThrow(() ->
						new ResourceNotFoundException(
								"Role not found with id : "+id) );
		
		return RoleMapper.mapToDto(role);
	}

	@Override
	public RoleDto updateRole(Long id, RoleDto roleDto) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() ->
					new ResourceNotFoundException(
							"Role not found with id : "+id) );
		
		role.setRoleName(roleDto.getRoleName());
		role.setDescription(roleDto.getDescription());
		
		Role updateRole = roleRepository.save(role);
		
		return RoleMapper.mapToDto(updateRole);
	}

	@Override
	public void deleteRole(Long id) {
		Role role = roleRepository.findById(id)
				.orElseThrow(() ->
					new ResourceNotFoundException(
							"Role not found with id : "+id) );
		
		roleRepository.delete(role);
	}
	
}
