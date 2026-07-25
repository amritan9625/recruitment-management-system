package com.amritan.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
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

import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.CandidateStatus;
import com.amritan.backend.service.CandidateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

	private CandidateService candidateService;
	@Autowired
	public void setCandidateService(CandidateService candidateService) {
		this.candidateService = candidateService;
	}
	
	@PostMapping
	public ResponseEntity<CandidateDto> createCandidate(@Valid @RequestBody CandidateDto dto){
		CandidateDto candidateDto = candidateService.createCandidate(dto);
		
		return new ResponseEntity<>(
				candidateDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<CandidateDto>> getAllCandidates(
	        								@RequestParam(defaultValue = "0") int pageNo,
	        								@RequestParam(defaultValue = "10") int pageSize,
	        								@RequestParam(defaultValue = "id") String sortBy,
	        								@RequestParam(defaultValue = "asc") String sortDir ){
		
		return ResponseEntity.ok(
				candidateService.getAllCandidates(
				        pageNo,
				        pageSize,
				        sortBy,
				        sortDir
				));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CandidateDto> getCandidateById(@PathVariable Long id){
		CandidateDto candidate = candidateService.getCandidateById(id);
		
		return ResponseEntity.ok(candidate);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CandidateDto> updateCandidate(@PathVariable Long id, @RequestBody CandidateDto dto){
		CandidateDto candidate = candidateService.updateCandidate(id, dto);
		
		return ResponseEntity.ok(candidate);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCandidate(@PathVariable Long id){
		candidateService.deleteCandidate(id);
		
		return ResponseEntity.ok("Candidate Deleted Successfully");
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<PageResponse<CandidateDto>> searchCandidates(
	        									@RequestParam String keyword,
	        									@RequestParam(defaultValue="0") int pageNo,
	        									@RequestParam(defaultValue="10") int pageSize){
		
		return ResponseEntity.ok(
				candidateService.searchCandidates(
						keyword,
						pageNo,
						pageSize));
	}
	
	
	@GetMapping("/status/{status}")
	public ResponseEntity<PageResponse<CandidateDto>> getCandidatesByStatus(
	        									@PathVariable CandidateStatus status,
	        									@RequestParam(defaultValue="0") int pageNo,
	        									@RequestParam(defaultValue="10") int pageSize ){
		
		return ResponseEntity.ok(
				candidateService.getCandidatesByStatus(status, pageNo, pageSize));
	}
	
}







