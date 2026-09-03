package com.amritan.backend.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.ApiResponse;
import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.InterviewStatus;
import com.amritan.backend.service.InterviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Tag(name = "Interview APIs", description = "APIs for managing interviews")
public class InterviewController {

	private final InterviewService interviewService;

	@PostMapping
	@Operation(summary = "Create an interview", description = "Creates a new interview", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Interview information", required = true))
	public ResponseEntity<ApiResponse<InterviewDto>> createInterview(@Valid @RequestBody InterviewDto interviewDto) {
		InterviewDto dto = interviewService.createInterview(interviewDto);

		return new ResponseEntity<>(new ApiResponse<>(true, "Interview created successfully", dto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all interviews", description = "Fetches interviews with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>>> getAllInterviews(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of interviews per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<InterviewDto> pageResponse = interviewService.getAllInterviews(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Interviews fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get interview by ID", description = "Fetches an interview using its ID")
	public ResponseEntity<ApiResponse<InterviewDto>> getInterviewById(
			@PathVariable @Parameter(description = "Unique ID of the interview", example = "1", required = true) Long id) {
		InterviewDto interviewDto = interviewService.getInterviewById(id);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Interview fetched successfully", interviewDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an interview", description = "Updates an existing interview", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated interview information", required = true))
	public ResponseEntity<ApiResponse<InterviewDto>> updateInterview(
			@PathVariable @Parameter(description = "Unique ID of the interview", example = "1", required = true) Long id,
			@Valid @RequestBody InterviewDto dto) {
		InterviewDto interviewDto = interviewService.updateInterview(id, dto);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Interview updated successfully", interviewDto, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an interview", description = "Deletes an existing interview")
	public ResponseEntity<ApiResponse<String>> deleteInterview(
			@PathVariable @Parameter(description = "Unique ID of the interview", example = "1", required = true) Long id) {
		interviewService.deleteInterview(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Interview deleted successfully", null, LocalDateTime.now()));
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get interviews by status", description = "Fetches interviews filtered by interview status")
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>>> getInterviewByStatus(
			@PathVariable @Parameter(description = "Interview status used for filtering", example = "SCHEDULED") InterviewStatus status,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of interviews per page", example = "10") int pageSize) {

		PageResponse<InterviewDto> pageResponse = interviewService.getInterviewByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Interview fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/interviewer/{keyword}")
	@Operation(summary = "Search interviews by interviewer", description = "Searches interviews using an interviewer keyword")
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>>> getInterviewer(
			@PathVariable @Parameter(description = "Keyword used to search interviews by interviewer", example = "Amit") String keyword,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of interviews per page", example = "10") int pageSize) {

		PageResponse<InterviewDto> pageResponse = interviewService.getInterviewer(keyword, pageNo, pageSize);

		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Interviewer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

}