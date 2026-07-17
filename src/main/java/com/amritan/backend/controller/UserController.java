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

import com.amritan.backend.dto.UserDto;
import com.amritan.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto){
		UserDto userDto = userService.createUserDto(dto);
		
		return new ResponseEntity<>(
				userDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> userDtos = userService.getAllUsers();
		
		return ResponseEntity.ok(userDtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
		UserDto userDto = userService.getUserById(id);
		
		return ResponseEntity.ok(userDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto dto){
		UserDto userDto = userService.updateUser(id, dto);
		
		return ResponseEntity.ok(userDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		
		return ResponseEntity.ok("User Deleted Successfully");
	}
}






