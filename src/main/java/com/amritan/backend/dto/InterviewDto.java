package com.amritan.backend.dto;

import java.time.LocalDateTime;

import com.amritan.backend.enums.InterviewStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

	private InterviewStatus status;

	@NotNull(message = "Application Id is must")
	@Positive(message = "Application Id must be positive")
	private Long applicationId;
}