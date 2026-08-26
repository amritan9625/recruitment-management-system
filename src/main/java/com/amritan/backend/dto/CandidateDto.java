package com.amritan.backend.dto;

import com.amritan.backend.enums.CandidateStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateDto {

	private Long id;
	
	
	@NotBlank(message = "Name must be required")
	@Size(min=3,max=50)
	private String firstName;
	
	private String lastName;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email is reuired")
	private String email;
	
	@NotBlank(message = "Phone no. is required")
	private String phone;
	
	@NotBlank(message = "Skills must be required")
	private String skills;
	
	@NotNull(message = "Experience is required")
	@Min(value = 0, message = "Experience cannot be negative")
	private Integer experience;
	
	@NotBlank(message = "Resume URL must be required")
	private String resumeUrl;
	
	@NotNull(message = "Status is required")
	private CandidateStatus status;
}
