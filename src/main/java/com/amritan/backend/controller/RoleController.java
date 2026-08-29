package com.amritan.backend.controller;

import java.time.LocalDateTime;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role APIs", description = "APIs for managing user roles")
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	@Operation(summary = "Create a role", description = "Creates a new user role", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Role information", required = true))
	public ResponseEntity<ApiResponse<RoleDto>> createRole(@Valid @RequestBody RoleDto roleDto) {
		RoleDto saveRoledDto = roleService.createRole(roleDto);

		return new ResponseEntity<>(
				new ApiResponse<>(true, "Role created successfully", saveRoledDto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all roles", description = "Fetches roles with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<RoleDto>>> getAllRoles(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of roles per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<RoleDto> pageResponse = roleService.getAllRoles(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<RoleDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Roles fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get role by ID", description = "Fetches a role using its ID")
	public ResponseEntity<ApiResponse<RoleDto>> getRoleById(
			@PathVariable @Parameter(description = "Unique ID of the role", example = "1", required = true) Long id) {
		RoleDto roleDto = roleService.getRoleById(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Role fetched successfully", roleDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a role", description = "Updates an existing role", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated role information", required = true))
	public ResponseEntity<ApiResponse<RoleDto>> updateRole(
			@PathVariable @Parameter(description = "Unique ID of the role", example = "1", required = true) Long id,
			@Valid @RequestBody RoleDto roleDto) {
		RoleDto updateDto = roleService.updateRole(id, roleDto);

		return ResponseEntity.ok(new ApiResponse<>(true, "Role updated successfully", updateDto, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a role", description = "Deletes an existing role")
	public ResponseEntity<ApiResponse<String>> deleteRole(
			@PathVariable @Parameter(description = "Unique ID of the role", example = "1", required = true) Long id) {
		roleService.deleteRole(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Role deleted successfully", null, LocalDateTime.now()));
	}

	@GetMapping("/roleName/{roleName}")
	@Operation(summary = "Get roles by name", description = "Fetches roles using the role name")
	public ResponseEntity<ApiResponse<PageResponse<RoleDto>>> getByRoleName(
			@PathVariable @Parameter(description = "Role name used for searching", example = "ADMIN") String roleName,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of roles per page", example = "10") int pageSize) {

		PageResponse<RoleDto> pageResponse = roleService.getByRoleName(roleName, pageNo, pageSize);

		ApiResponse<PageResponse<RoleDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Role fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);

	}
}