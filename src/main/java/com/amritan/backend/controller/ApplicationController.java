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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.enums.ApplicationStatus;
import com.amritan.backend.service.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

	private ApplicationService applicationService;
	
	@Autowired
	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}
	
	@PostMapping
	public ResponseEntity<ApplicationDto> createApplication(@Valid @RequestBody ApplicationDto applicationDto){
		ApplicationDto dto = applicationService.createApplication(applicationDto);
		
		return new ResponseEntity<>(
				dto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ApplicationDto>> getAllApplications(){
		List<ApplicationDto> applicationDtos = applicationService.getAllApplications();
		
		return ResponseEntity.ok(applicationDtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id){
		ApplicationDto applicationDto = applicationService.getApplicationById(id);
		
		return ResponseEntity.ok(applicationDto);
	}
	
	
	
	@GetMapping("/job/{jobId}")
	public ResponseEntity<List<ApplicationDto>> getApplicationsByJob(@PathVariable Long jobId) {
	    
		List<ApplicationDto> applicationDtos = applicationService.getApplicationsByJob(jobId);
		
		return ResponseEntity.ok(applicationDtos);
	}
	
	
	
	@GetMapping("/candidate/{candidateId}")
	public ResponseEntity<List<ApplicationDto>> getApplicationsByCandidate(@PathVariable Long candidateId) {
	    List<ApplicationDto> applicationDtos = applicationService.getApplicationsByCandidate(candidateId);
	    
	    return ResponseEntity.ok(applicationDtos);
	}
	
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ApplicationDto> updateStatus(
	        @PathVariable Long id,
	        @RequestParam ApplicationStatus status) {

	    ApplicationDto applicationDto = applicationService.updateStatus(id, status);
	    
	    return ResponseEntity.ok(applicationDto);
	}
		
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApplicationDto> updateApplication(@PathVariable Long id, @RequestBody ApplicationDto dto){
		ApplicationDto applicationDto = applicationService.updateApplication(id, dto);
		
		return ResponseEntity.ok(applicationDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable Long id){
		applicationService.deleteApplication(id);
		
		return ResponseEntity.ok("Application deleted Successfully");
	}
	
}







