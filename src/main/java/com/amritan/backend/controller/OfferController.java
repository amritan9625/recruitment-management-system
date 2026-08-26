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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

	private final OfferService offerService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<OfferDto>> createOffer(@Valid @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.createOffer(dto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "Offer created successfully"
				, offerDto, LocalDateTime.now()) , HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>> > getAllOffers(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<OfferDto> pageResponse = offerService.getAllOffers
				(pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offers fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<OfferDto>> getOfferById(@PathVariable Long id){
		OfferDto offerDto = offerService.getOfferById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Offer fetched successfully"
				, offerDto, LocalDateTime.now()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<OfferDto>> updateOffer(
			@PathVariable Long id, @Valid @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.updateOffer(id, dto);
		
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Offer updated successfully"
				, offerDto, LocalDateTime.now()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteOffer(@PathVariable Long id){
		offerService.deleteOffer(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Offer deleted successfully"
				, null, LocalDateTime.now()));
	}
	
	
	
	@GetMapping("/salary/{salary}")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferBySalary(
			@PathVariable Double salary,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferBySalary(salary, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/candidateName/{candidateName}")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByCandidateName(
			@PathVariable String candidateName,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferByCandidateName(candidateName, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/jobTitle/{jobTitle}")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByJobTitle(
			@PathVariable String jobTitle,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferByJobTitle(jobTitle, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByStatus(
			@PathVariable OfferStatus status,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferByStatus(status, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
}