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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

	private final CandidateService candidateService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<CandidateDto>> createCandidate(@Valid @RequestBody CandidateDto dto){
		CandidateDto candidateDto = candidateService.createCandidate(dto);
		
		return new ResponseEntity<>( new ApiResponse<>(true, "Candidate created successfully", 
									candidateDto,
									LocalDateTime.now()) , HttpStatus.CREATED);
	}
	
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>> > getAllCandidates(
	        								@RequestParam(defaultValue = "0") int pageNo,
	        								@RequestParam(defaultValue = "10") int pageSize,
	        								@RequestParam(defaultValue = "id") String sortBy,
	        								@RequestParam(defaultValue = "asc") String sortDir ){
		
		
		PageResponse<CandidateDto> pageResponse = candidateService.getAllCandidates(pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Candidates created successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CandidateDto>> getCandidateById(@PathVariable Long id){
		CandidateDto candidateDto = candidateService.getCandidateById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Candidate fetched successfully",
										candidateDto,
										LocalDateTime.now() ) );
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CandidateDto>> updateCandidate(@PathVariable Long id, @RequestBody CandidateDto dto){
		CandidateDto candidate = candidateService.updateCandidate(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Candidate updated successfully",
				candidate,
				LocalDateTime.now() ) );
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteCandidate(@PathVariable Long id){
		candidateService.deleteCandidate(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Candidate deleted successfully",
				null,
				LocalDateTime.now() ) );
		
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>> > searchCandidates(
	        									@RequestParam String keyword,
	        									@RequestParam(defaultValue="0") int pageNo,
	        									@RequestParam(defaultValue="10") int pageSize){
		
		 PageResponse<CandidateDto> pageResponse =
		            candidateService.searchCandidates(keyword, pageNo, pageSize);

		    ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();

		    response.setSuccess(true);
		    response.setMessage("Candidates fetched successfully");
		    response.setData(pageResponse);
		    response.setTimestamp(LocalDateTime.now());

		    return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ApiResponse<PageResponse<CandidateDto>>> getCandidatesByStatus(
	        @PathVariable CandidateStatus status,
	        @RequestParam(defaultValue = "0") int pageNo,
	        @RequestParam(defaultValue = "10") int pageSize) {

	    PageResponse<CandidateDto> pageResponse =
	            candidateService.getCandidatesByStatus(status, pageNo, pageSize);

	    ApiResponse<PageResponse<CandidateDto>> response = new ApiResponse<>();

	    response.setSuccess(true);
	    response.setMessage("Candidates fetched successfully");
	    response.setData(pageResponse);
	    response.setTimestamp(LocalDateTime.now());

	    return ResponseEntity.ok(response);
	}
	
}