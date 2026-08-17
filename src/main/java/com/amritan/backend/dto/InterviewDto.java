package com.amritan.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InterviewDto {

	private Long id;

	@NotNull(message = "Interview date is required")
	private LocalDateTime interviewDate;

	@NotBlank(message = "Interviewer name is required")
	private String interviewer;

	@NotBlank(message = "Mode of interview is required")
	private String mode;

	@NotBlank(message = "Status is required")
	private String status;

	@NotNull(message = "Application Id is must")
	private Long applicationId;
}