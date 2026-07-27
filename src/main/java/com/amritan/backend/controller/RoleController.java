package com.amritan.backend.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.ApiResponse;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.RoleDto;
import com.amritan.backend.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private RoleService roleService;
	
	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	// create role
	@PostMapping
	public ResponseEntity<ApiResponse<RoleDto>> createRole(@Valid @RequestBody RoleDto roleDto){
		RoleDto saveRoledDto = roleService.createRole(roleDto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "Role created successfully"
				, saveRoledDto, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	// get all roles
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<RoleDto>> > getAllRoles(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<RoleDto> pageResponse = roleService.getAllRoles(
				pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<RoleDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Role fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);		
	}
	
	// get role by id
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RoleDto>> getRoleById(@PathVariable Long id){
		RoleDto roleDto = roleService.getRoleById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Role fetched successfully"
				, roleDto, LocalDateTime.now()));
	}
	
	// update role
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<RoleDto>> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto){
		RoleDto updateDto  = roleService.updateRole(id, roleDto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Role updated successfully"
				, updateDto, LocalDateTime.now()));
	}
	
	// delete role
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable Long id){
		roleService.deleteRole(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Role deleted successfully"
				, null, LocalDateTime.now()));
	}
	
	@GetMapping("role_name")
	public ResponseEntity<ApiResponse<PageResponse<RoleDto>> > getByRoleName(@RequestParam String roleName
			, @RequestParam(defaultValue = "0") int pageNo
			, @RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<RoleDto> pageResponse = roleService.getByRoleName(roleName, pageNo, pageSize);
		
		ApiResponse<PageResponse<RoleDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Roles fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
		
	}
}








