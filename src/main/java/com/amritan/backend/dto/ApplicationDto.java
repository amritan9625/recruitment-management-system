package com.amritan.backend.dto;

import com.amritan.backend.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

	private Long id;
	
	@NotNull(message = "Candidate Id is required")
	@Positive(message = "Candidate Id must be positive")
	private Long candidateId;
	
	@NotNull(message = "Job Id is must")
	@Positive(message = "Job Id must be positive")
	private Long jobId;
	
	@NotNull(message = "Status is required")
	private ApplicationStatus status;	// ApplicationStatus will automatically converts the JSON string into the enum
}
