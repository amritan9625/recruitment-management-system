package com.amritan.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDto {

	private Long id;
	
	@NotBlank(message = "Role name is required")
	private String roleName;
	
	@NotBlank(message = "Description is required")
	@Size(min=10,max=100)
	private String description;
	
}
