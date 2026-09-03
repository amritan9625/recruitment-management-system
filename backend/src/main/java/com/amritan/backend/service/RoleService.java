package com.amritan.backend.service;


import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.RoleDto;

public interface RoleService {

	RoleDto createRole(RoleDto roleDto);
	
	PageResponse<RoleDto> getAllRoles(int pageNo, int pageSize, String sortBy, String sortDir);
	
	RoleDto getRoleById(Long id);
	
	RoleDto updateRole(Long id, RoleDto roleDto);
	
	void deleteRole(Long id);
	
	PageResponse<RoleDto> getByRoleName(String roleName, int pageNo, int pageSize);

}
