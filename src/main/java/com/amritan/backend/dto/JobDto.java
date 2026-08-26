package com.amritan.backend.dto;

import com.amritan.backend.enums.JobStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

	private Long id;
	
	@NotBlank(message = "Job title is required")
	private String title;
	
	@NotBlank(message = "Job description is required")
	@Size(min=10,max=100)
	private String description;
	
	@NotBlank(message = "Job location is required")
	private String location;
	
	@NotNull(message = "Salary is required")
	@Positive(message = "Salary must be greater than 0")
	private Double salary;
	
	@NotBlank(message = "Job type is required")
	private String jobType;
	
	@NotNull(message = "Job status is required")
	private JobStatus status;
}
