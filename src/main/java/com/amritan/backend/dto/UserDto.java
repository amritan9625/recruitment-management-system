package com.amritan.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	
	@NotBlank(message = "Username is required")
	@Size(min=3,max=50)
	private String name;
	
	@Email(message = "Invalid email format")
	@NotBlank(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Phone no. is required")
	private String phone;
	
	@NotNull(message = "Role ID is required")
	private Long roleId;
}
