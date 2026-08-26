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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<UserResponseDto> > createUser(@Valid @RequestBody CreateUserRequest request){
		UserResponseDto userResponse = userService.createUser(request);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "User created successfully",
				userResponse, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>> > getAllUsers(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<UserResponseDto> pageResponse = userService.getAllUsers(
				pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id){
		UserResponseDto userResponse = userService.getUserById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully",
				userResponse, LocalDateTime.now()));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
			@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request){
		
		UserResponseDto userResponse = userService.updateUser(id, request);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully",
				userResponse, LocalDateTime.now()));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User Deleted Successfully", 
											null, LocalDateTime.now()));
	}
	
	
	@GetMapping("/name/{name}")
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getUserByName(
			@PathVariable String name, 
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<UserResponseDto> pageResponse = userService.getUsersByName(name, pageNo, pageSize);
		
		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("User fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ApiResponse<PageResponse<UserResponseDto>>> getUserByEmail(
			@PathVariable String email,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<UserResponseDto> pageResponse = userService.getUsersByEmail(email, pageNo, pageSize);
		
		ApiResponse<PageResponse<UserResponseDto>> response = new ApiResponse<>();
	
		response.setSuccess(true);
		response.setMessage("User fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
	
		return ResponseEntity.ok(response);
	}
}






