package com.amritan.backend.mapper;


import com.amritan.backend.dto.RoleDto;
import com.amritan.backend.entity.Role;

public class RoleMapper {

	public static Role mapToEntity(RoleDto dto) {
		
		Role role = new Role();
		
		role.setId(dto.getId());
		role.setRoleName(dto.getRoleName());
		role.setDescription(dto.getDescription());
		
		return role;
	}
	
	public static RoleDto mapToDto(Role role){
		RoleDto dto = new RoleDto();
		
		dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setDescription(role.getDescription());

        return dto;
	}
	
}
