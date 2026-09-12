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
import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.ApplicationStatus;
import com.amritan.backend.service.ApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Application APIs", description = "APIs for managing job applications")
public class ApplicationController {

	private final ApplicationService applicationService;

	@PostMapping
	@Operation(summary = "Create an application", description = "Creates a new job application", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Application information", required = true))
	public ResponseEntity<ApiResponse<ApplicationDto>> createApplication(@Valid @RequestBody ApplicationDto dto) {
		ApplicationDto applicationDto = applicationService.createApplication(dto);

		return new ResponseEntity<>(
				new ApiResponse<>(true, "Application created successfully", applicationDto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}
	
	
	@PostMapping("/me/{jobId}")
	public ResponseEntity<ApiResponse<ApplicationDto>> applyForJob(@PathVariable Long jobId) {
	    ApplicationDto applicationDto = applicationService.applyForJob(jobId);

	    return ResponseEntity.ok(new ApiResponse<>(true, "Application submitted successfully", applicationDto, LocalDateTime.now())
	    );
	}
	

	@GetMapping
	@Operation(summary = "Get all applications", description = "Fetches applications with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getAllApplications(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of applications per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<ApplicationDto> pageResponse = applicationService.getAllApplications(pageNo, pageSize, sortBy,
				sortDir);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Applications fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/my")
	@Operation(
	        summary = "Get my applications",
	        description = "Fetches applications belonging to the currently logged-in candidate"
	)
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getMyApplications(
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize) {

	    PageResponse<ApplicationDto> pageResponse =
	            applicationService.getMyApplications(pageNo, pageSize);

	    ApiResponse<PageResponse<ApplicationDto>> response =
	            new ApiResponse<>();

	    response.setSuccess(true);
	    response.setMessage("Your applications fetched successfully");
	    response.setData(pageResponse);
	    response.setTimestamp(LocalDateTime.now());

	    return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get application by ID", description = "Fetches an application using its ID")
	public ResponseEntity<ApiResponse<ApplicationDto>> getApplicationById(
			@PathVariable @Parameter(description = "Unique ID of the application", example = "1", required = true) Long id) {
		ApplicationDto applicationDto = applicationService.getApplicationById(id);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Application fetched successfully", applicationDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an application", description = "Updates an existing application", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated Application information", required = true))
	public ResponseEntity<ApiResponse<ApplicationDto>> updateApplication(
			@PathVariable @Parameter(description = "Unique ID of the application", example = "1", required = true) Long id,
			@Valid @RequestBody ApplicationDto dto) {
		ApplicationDto applicationDto = applicationService.updateApplication(id, dto);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Application updated successfully", applicationDto, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an application", description = "Deletes an existing application")
	public ResponseEntity<ApiResponse<String>> deleteApplication(
			@PathVariable @Parameter(description = "Unique ID of the application", example = "1", required = true) Long id) {
		applicationService.deleteApplication(id);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Application deleted Successfully", null, LocalDateTime.now()));
	}

	@PutMapping("/{id}/{status}")
	@Operation(summary = "Update application status", description = "Updates the status of an application")
	public ResponseEntity<ApiResponse<ApplicationDto>> updateStatus(
			@PathVariable @Parameter(description = "Unique ID of the application", example = "1", required = true) Long id,
			@PathVariable @Parameter(description = "Application status", example = "SHORTLISTED") ApplicationStatus status) {

		ApplicationDto applicationDto = applicationService.updateStatus(id, status);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Application updated successfully", applicationDto, LocalDateTime.now()));
	}

	@GetMapping("/candidate/{candidateId}")
	@Operation(summary = "Get applications by candidate Id", description = "Fetches applications belonging to a particular candidate with pagination")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationsByCandidateId(
			@PathVariable @Parameter(description = "ID of the candidate", example = "1") Long candidateId,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page", example = "10") int pageSize) {

		PageResponse<ApplicationDto> pageResponse = applicationService.getApplicationsByCandidateId(candidateId, pageNo,
				pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Application fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/job/{jobId}")
	@Operation(summary = "Get applications by job Id", description = "Fetches applications for a particular job with pagination")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationsByJobId(
			@PathVariable @Parameter(description = "ID of the job", example = "1") Long jobId,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page", example = "10") int pageSize) {

		PageResponse<ApplicationDto> pageResponse = applicationService.getApplicationsByJobId(jobId, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Application fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get applications by status", description = "Fetches applications filtered by application status")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationByStatus(
			@PathVariable @Parameter(description = "Application status", example = "SHORTLISTED") ApplicationStatus status,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page", example = "10") int pageSize) {

		PageResponse<ApplicationDto> pageResponse = applicationService.getApplicationByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Application fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/candidateName/{candidateName}")
	@Operation(summary = "Get applications by candidate name", description = "Fetches applications using the candidate name")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationByCandidateName(
			@PathVariable @Parameter(description = "Candidate name used for searching applications", example = "Rahul") String candidateName,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page", example = "10") int pageSize) {
		PageResponse<ApplicationDto> pageResponse = applicationService.getApplicationByCandidateName(candidateName,
				pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Application fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/jobTitle/{jobTitle}")
	@Operation(summary = "Get applications by job title", description = "Fetches applications using the job title")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationByJobTitle(
			@PathVariable @Parameter(description = "Job title used for searching applications", example = "Java Developer") String jobTitle,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of records per page", example = "10") int pageSize) {
		PageResponse<ApplicationDto> pageResponse = applicationService.getApplicationByJobTitle(jobTitle, pageNo,
				pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Application fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
}
