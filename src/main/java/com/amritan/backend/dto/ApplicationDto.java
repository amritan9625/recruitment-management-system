package com.amritan.backend.dto;

import com.amritan.backend.enums.ApplicationStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

	private Long id;
	
	@NotNull
	private Long candidateId;
	
	@NotNull
	private Long jobId;
	
	private ApplicationStatus status;	// ApplicationStatus will automatically converts the JSON string into the enum
}
