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
import com.amritan.backend.dto.UserDto;
import com.amritan.backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<UserDto> > createUser(@Valid @RequestBody UserDto dto){
		UserDto userDto = userService.createUserDto(dto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "User created successfully",
				userDto, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<UserDto>> > getAllUsers(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<UserDto> pageResponse = userService.getAllUsers(
				pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<UserDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id){
		UserDto userDto = userService.getUserById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User fetched successfully",
				userDto, LocalDateTime.now()));
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<UserDto>> updateUser(@PathVariable Long id, @RequestBody UserDto dto){
		UserDto userDto = userService.updateUser(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully",
				userDto, LocalDateTime.now()));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "User Deleted Successfully", 
											null, LocalDateTime.now()));
	}
	
	
	@GetMapping("/name")
	public ResponseEntity<ApiResponse<PageResponse<UserDto>>> getUserByName(@RequestParam String keyword, 
									@RequestParam(defaultValue = "0") int pageNo,
									@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<UserDto> pageResponse = 
					userService.getUsersByName(keyword, pageNo, pageSize);
		ApiResponse<PageResponse<UserDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/email")
	public ResponseEntity<ApiResponse<PageResponse<UserDto>>> getUserByEmail(@RequestParam String keyword,
										@RequestParam(defaultValue = "0") int pageNo,
										@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<UserDto> pageResponse = 
				userService.getUsersByEmail(keyword, pageNo, pageSize);
		ApiResponse<PageResponse<UserDto>> response = new ApiResponse<>();
	
		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
	
		return ResponseEntity.ok(response);
	}
}






