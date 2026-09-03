package com.amritan.backend.dto;

import java.time.LocalDate;

import com.amritan.backend.enums.OfferStatus;
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

	@NotNull(message = "Status is required")
	private OfferStatus status;

	@NotNull(message = "Candidate Id is required")
	@Positive(message = "Candidate Id must be positive")
	private Long candidateId;

	@NotNull(message = "Job Id is required")
	@Positive(message = "Job Id must be positive")
	private Long jobId;
}
