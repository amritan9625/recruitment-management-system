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
import com.amritan.backend.dto.CreateUserRequest;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.dto.UpdateUserRequest;
import com.amritan.backend.dto.UserResponseDto;
import com.amritan.backend.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "APIs for managing users")
public class UserController {

	private final UserService userService;

	@PostMapping
	@Operation(summary = "Create a user", description = "Creates a new user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User information", required = true))
	public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody CreateUserRequest request) {
		UserResponseDto userResponse = userService.createUser(request);

		return new ResponseEntity<>(
				new ApiResponse<>(true, "User created successfully", userResponse, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all users", description = "Fetches users with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getAllUsers(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of users per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<UserResponseDto> pageResponse = userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get user by ID", description = "Fetches a user using its ID")
	public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(
			@PathVariable @Parameter(description = "Unique ID of the user", example = "1", required = true) Long id) {
		UserResponseDto userResponse = userService.getUserById(id);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "User fetched successfully", userResponse, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a user", description = "Updates an existing user", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user information", required = true))
	public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
			@PathVariable @Parameter(description = "Unique ID of the user", example = "1", required = true) Long id,
			@Valid @RequestBody UpdateUserRequest request) {

		UserResponseDto userResponse = userService.updateUser(id, request);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "User updated successfully", userResponse, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a user", description = "Deletes an existing user")
	public ResponseEntity<ApiResponse<String>> deleteUser(
			@PathVariable @Parameter(description = "Unique ID of the user", example = "1", required = true) Long id) {
		userService.deleteUser(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "User Deleted Successfully", null, LocalDateTime.now()));
	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Get users by name", description = "Fetches users using the user's name")
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getUserByName(
			@PathVariable @Parameter(description = "User name used for searching", example = "Amit") String name,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of users per page", example = "10") int pageSize) {

		PageResponse<UserResponseDto> pageResponse = userService.getUsersByName(name, pageNo, pageSize);

		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("User fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "Get users by email", description = "Fetches users using the user's email")
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getUserByEmail(
			@PathVariable @Parameter(description = "User email used for searching", example = "amit@example.com") String email,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of users per page", example = "10") int pageSize) {

		PageResponse<UserResponseDto> pageResponse = userService.getUsersByEmail(email, pageNo, pageSize);

		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("User fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
}
