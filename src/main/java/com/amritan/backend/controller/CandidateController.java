package com.amritan.backend.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.CandidateDto;
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
	public ResponseEntity<List<CandidateDto>> getAllCandidates(){
		List<CandidateDto> candidates = candidateService.getAllCandidates();
		
		return ResponseEntity.ok(candidates);
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
}







