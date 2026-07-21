package com.amritan.backend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OfferDto {

	private Long id;

	@NotNull(message = "Salary is required")
	@Positive(message = "Salary must be greater than 0")
	private Double salary;

	@NotNull(message = "Joining date is required")
	private LocalDate joiningDate;

	@NotBlank(message = "Status is required")
	private String status;

	@NotNull(message = "Candidate Id is required")
	private Long candidateId;

	@NotNull(message = "Job Id is required")
	private Long jobId;
}
