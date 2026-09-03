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
import com.amritan.backend.dto.OfferDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.OfferStatus;
import com.amritan.backend.service.OfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
@Tag(name = "Offer APIs", description = "APIs for managing job offers")
public class OfferController {

	private final OfferService offerService;

	@PostMapping
	@Operation(summary = "Create an offer", description = "Creates a new job offer", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Offer information", required = true))
	public ResponseEntity<ApiResponse<OfferDto>> createOffer(@Valid @RequestBody OfferDto dto) {
		OfferDto offerDto = offerService.createOffer(dto);

		return new ResponseEntity<>(
				new ApiResponse<>(true, "Offer created successfully", offerDto, LocalDateTime.now()),
				HttpStatus.CREATED);
	}

	@GetMapping
	@Operation(summary = "Get all offers", description = "Fetches offers with pagination and sorting")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getAllOffers(
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of offers per page", example = "10") int pageSize,
			@RequestParam(defaultValue = "id") @Parameter(description = "Field used for sorting", example = "id") String sortBy,
			@RequestParam(defaultValue = "asc") @Parameter(description = "Sort direction: asc or desc", example = "asc") String sortDir) {

		PageResponse<OfferDto> pageResponse = offerService.getAllOffers(pageNo, pageSize, sortBy, sortDir);

		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Offers fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get offer by ID", description = "Fetches an offer using its ID")
	public ResponseEntity<ApiResponse<OfferDto>> getOfferById(
			@PathVariable @Parameter(description = "Unique ID of the offer", example = "1", required = true) Long id) {
		OfferDto offerDto = offerService.getOfferById(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Offer fetched successfully", offerDto, LocalDateTime.now()));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an offer", description = "Updates an existing offer", 
	requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated offer information", required = true))
	public ResponseEntity<ApiResponse<OfferDto>> updateOffer(
			@PathVariable @Parameter(description = "Unique ID of the offer", example = "1", required = true) Long id,
			@Valid @RequestBody OfferDto dto) {
		OfferDto offerDto = offerService.updateOffer(id, dto);

		return ResponseEntity.ok(new ApiResponse<>(true, "Offer updated successfully", offerDto, LocalDateTime.now()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an offer", description = "Deletes an existing offer")
	public ResponseEntity<ApiResponse<String>> deleteOffer(
			@PathVariable @Parameter(description = "Unique ID of the offer", example = "1", required = true) Long id) {
		offerService.deleteOffer(id);

		return ResponseEntity.ok(new ApiResponse<>(true, "Offer deleted successfully", null, LocalDateTime.now()));
	}

	@GetMapping("/salary/{salary}")
	@Operation(summary = "Get offers by salary", description = "Fetches offers filtered by salary")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferBySalary(
			@PathVariable @Parameter(description = "Salary amount used for filtering offers", example = "75000.0") Double salary,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of offers per page", example = "10") int pageSize) {

		PageResponse<OfferDto> pageResponse = offerService.getOfferBySalary(salary, pageNo, pageSize);

		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/candidateName/{candidateName}")
	@Operation(summary = "Get offers by candidate name", description = "Fetches offers using the candidate name")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByCandidateName(
			@PathVariable @Parameter(description = "Candidate name used for searching offers", example = "Rahul") String candidateName,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of offers per page", example = "10") int pageSize) {

		PageResponse<OfferDto> pageResponse = offerService.getOfferByCandidateName(candidateName, pageNo, pageSize);

		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/jobTitle/{jobTitle}")
	@Operation(summary = "Get offers by job title", description = "Fetches offers using the job title")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByJobTitle(
			@PathVariable @Parameter(description = "Job title used for searching offers", example = "Java Developer") String jobTitle,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of offers per page", example = "10") int pageSize) {

		PageResponse<OfferDto> pageResponse = offerService.getOfferByJobTitle(jobTitle, pageNo, pageSize);

		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Get offers by status", description = "Fetches offers filtered by offer status")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByStatus(
			@PathVariable @Parameter(description = "Offer status used for filtering", example = "ACCEPTED") OfferStatus status,
			@RequestParam(defaultValue = "0") @Parameter(description = "Page number (zero-based)", example = "0") int pageNo,
			@RequestParam(defaultValue = "10") @Parameter(description = "Number of offers per page", example = "10") int pageSize) {

		PageResponse<OfferDto> pageResponse = offerService.getOfferByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
}