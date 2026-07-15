package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.RoleDto;

public interface RoleService {

	RoleDto createRole(RoleDto roleDto);
	
	List<RoleDto> getAllRoles();
	
	RoleDto getRoleById(Long id);
	
	RoleDto updateRole(Long id, RoleDto roleDto);
	
	void deleteRole(Long id);

}
