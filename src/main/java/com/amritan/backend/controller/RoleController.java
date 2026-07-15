package com.amritan.backend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import com.amritan.backend.dto.RoleDto;
import com.amritan.backend.service.RoleService;

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
	public ResponseEntity<RoleDto> createRole( @RequestBody RoleDto roleDto){
		RoleDto saveRoledDto = roleService.createRole(roleDto);
		
		return new ResponseEntity<>(
					saveRoledDto,
					HttpStatus.CREATED);
	}
	
	// get all roles
	@GetMapping
	public ResponseEntity<List<RoleDto>> getAllRoles(){
		List<RoleDto> rolesDtos = roleService.getAllRoles();
		
		return ResponseEntity.ok(rolesDtos);
	}
	
	// get role by id
	@GetMapping("/{id}")
	public ResponseEntity<RoleDto> getRoleById(@PathVariable Long id){
		RoleDto roleDto = roleService.getRoleById(id);
		
		return ResponseEntity.ok(roleDto);
	}
	
	// update role
	@PutMapping("/{id}")
	public ResponseEntity<RoleDto> updateRole(@PathVariable Long id, @RequestBody RoleDto roleDto){
		RoleDto updatedRole = roleService.updateRole(id, roleDto);
		
		return ResponseEntity.ok(updatedRole);
	}
	
	// delete role
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable Long id){
		roleService.deleteRole(id);
		
		return ResponseEntity.ok("Role deleted successfully");
	}
}








