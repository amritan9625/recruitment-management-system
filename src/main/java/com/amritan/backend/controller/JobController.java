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
import com.amritan.backend.dto.JobDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.JobStatus;
import com.amritan.backend.service.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Tag(name = "Job APIs", description = "APIs for managing jobs")
public class JobController {

	private final JobService jobService;

	@PostMapping
	@Operation(summary = "Create a job", description = "Creates a new job", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Job information", required = true))
	public ResponseEntity<ApiResponse<JobDto>> createJob(@Valid @RequestBody JobDto jobtDto) {
		JobDto jobDto = jobService.createJob(jobtDto);

		return new ResponseEntity<>(new ApiResponse<>(true, "Job created successfully", jobDto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all jobs", description = "Fetches jobs with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<JobDto>>> getAllJobs(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of jobs per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<JobDto> pageResponse = jobService.getAllJobs(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Jobs fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get job by ID", description = "Fetches a job using its ID")
	public ResponseEntity<ApiResponse<JobDto>> getJobById(
			@PathVariable @Parameter(description = "Unique ID of the job", example = "1", required = true) Long id) {
		JobDto jobDto = jobService.getJobById(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Job fetched successfully", jobDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a job", description = "Updates an existing job using its ID", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated job information", required = true))
	public ResponseEntity<ApiResponse<JobDto>> updateJob(
			@PathVariable @Parameter(description = "Unique ID of the job", example = "1", required = true) Long id,
			@Valid @RequestBody JobDto dto) {
		JobDto updateDto = jobService.updateJob(id, dto);

		return ResponseEntity.ok(new ApiResponse<>(true, "Job updated successfully", updateDto, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a job", description = "Deletes an existing job using its ID")
	public ResponseEntity<ApiResponse<String>> deleteJob(
			@PathVariable @Parameter(description = "Unique ID of the job", example = "1", required = true) Long id) {
		jobService.deleteJob(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Job Deleted Successfully", null, LocalDateTime.now()));
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get jobs by status", description = "Fetches jobs filtered by job status with pagination")
	public ResponseEntity<ApiResponse<PageResponse<JobDto>>> getJobsByStatus(
			@PathVariable @Parameter(description = "Job status used for filtering", example = "OPEN") JobStatus status,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of candidates per page", example = "10") int pageSize) {

		PageResponse<JobDto> pageResponse = jobService.getJobsByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Jobs fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/location/{location}")
	@Operation(summary = "Get jobs by location", description = "Fetches jobs filtered by location with pagination")
	public ResponseEntity<ApiResponse<PageResponse<JobDto>>> getJobsByLocation(
			@PathVariable @Parameter(description = "Job location used for filtering", example = "Delhi") String location,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of candidates per page", example = "10") int pageSize) {

		PageResponse<JobDto> pageResponse = jobService.getJobsByLocation(location, pageNo, pageSize);

		ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Jobs fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

}
