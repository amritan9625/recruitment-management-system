package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.PageResponse;
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
	public PageResponse<RoleDto> getAllRoles(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Role> page = roleRepository.findAll(pageable);
		
		List<RoleDto> content = page.getContent()
				.stream()
				.map(RoleMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				, page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
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

	@Override
	public PageResponse<RoleDto> getByRoleName(String roleName, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize,sort);
		
		Page<Role> page = roleRepository.findByRoleNameContainingIgnoreCase(roleName, pageable);
		
		List<RoleDto> content = page.getContent()
				.stream()
				.map(RoleMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				, page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}
	
}
