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
import com.amritan.backend.service.OfferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

	private final OfferService offerService;
	
	
	@PostMapping
	public ResponseEntity<OfferDto> createOffer(@Valid @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.createOffer(dto);
		
		return new ResponseEntity<>(
				offerDto, HttpStatus.CREATED);
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
	public ResponseEntity<OfferDto> getOfferById(@PathVariable Long id){
		OfferDto offerDto = offerService.getOfferById(id);
		
		return ResponseEntity.ok(offerDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OfferDto> updateOffer(@PathVariable Long id, @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.updateOffer(id, dto);
		
		return ResponseEntity.ok(offerDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOffer(@PathVariable Long id){
		offerService.deleteOffer(id);
		
		return ResponseEntity.ok("Offer deleted successfully");
	}
	
	
	
	@GetMapping("/salary/{salary}")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferBySalary(
			@PathVariable Double salary,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferBySalary(salary, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offers fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/search/candidate")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByCandidateName(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferByCandidateName(keyword, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offers fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/search/job")
	public ResponseEntity<ApiResponse<PageResponse<OfferDto>>> getOfferByJobTitle(
			@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<OfferDto> pageResponse = offerService.getOfferByJobTitle(keyword, pageNo, pageSize);
		
		ApiResponse<PageResponse<OfferDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Offers fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
}