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
import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.CandidateStatus;
import com.amritan.backend.service.CandidateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
@Tag(name = "Candidate APIs", description = "APIs for managing candidates")
public class CandidateController {

	private final CandidateService candidateService;

	@PostMapping
	@Operation(summary = "Create a candidate", description = "Creates a new candidate in the recruitment management system", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Candidate information", required = true))
	public ResponseEntity<ApiResponse<CandidateDto>> createCandidate(@Valid @RequestBody CandidateDto dto) {
		CandidateDto candidateDto = candidateService.createCandidate(dto);

		return new ResponseEntity<>(
				new ApiResponse<>(true, "Candidate created successfully", candidateDto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all candidates", description = "Fetches candidates with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>>> getAllCandidates(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of candidates per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<CandidateDto> pageResponse = candidateService.getAllCandidates(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Candidates fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);

	}

	@GetMapping("/{id}")
	@Operation(summary = "Get candidate by ID", description = "Fetches a candidate using the candidate ID")
	public ResponseEntity<ApiResponse<CandidateDto>> getCandidateById(
			@PathVariable @Parameter(description = "Unique ID of the candidate", example = "1", required = true) Long id) {
		CandidateDto candidateDto = candidateService.getCandidateById(id);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Candidate fetched successfully", candidateDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a candidate", description = "Updates an existing candidate using the candidate ID", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated candidate information", required = true))
	public ResponseEntity<ApiResponse<CandidateDto>> updateCandidate(
			@PathVariable @Parameter(description = "Unique ID of the candidate", example = "1", required = true) Long id,
			@Valid @RequestBody CandidateDto dto) {
		CandidateDto candidate = candidateService.updateCandidate(id, dto);

		return ResponseEntity
				.ok(new ApiResponse<>(true, "Candidate updated successfully", candidate, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a candidate", description = "Deletes an existing candidate using the candidate ID")
	public ResponseEntity<ApiResponse<String>> deleteCandidate(
			@PathVariable @Parameter(description = "Unique ID of the candidate", example = "1", required = true) Long id) {
		candidateService.deleteCandidate(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Candidate deleted successfully", null, LocalDateTime.now()));

	}

	@GetMapping("/candidateName/{candidateName}")
	@Operation(summary = "Search candidates by name", description = "Searches candidates by candidate name with pagination")
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>>> searchCandidates(
			@PathVariable @Parameter(description = "Candidate name to search for", example = "Rahul") String candidateName,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of candidates per page", example = "10") int pageSize) {

		PageResponse<CandidateDto> pageResponse = candidateService.searchCandidates(candidateName, pageNo, pageSize);

		ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Candidates fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get candidates by status", description = "Fetches candidates filtered by candidate status with pagination")
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>>> getCandidatesByStatus(
			@PathVariable @Parameter(description = "Candidate status used for filtering", example = "ACTIVE") CandidateStatus status,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of candidates per page", example = "10") int pageSize) {

		PageResponse<CandidateDto> pageResponse = candidateService.getCandidatesByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Candidates fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

}