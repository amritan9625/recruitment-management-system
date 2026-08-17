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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

	private final ApplicationService applicationService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<ApplicationDto>> createApplication(@Valid @RequestBody ApplicationDto dto){
		ApplicationDto applicationDto = applicationService.createApplication(dto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "Application created successfully"
				, applicationDto, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getAllApplications(
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<ApplicationDto> pageResponse = applicationService.getAllApplications(
				pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Applications fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ApplicationDto>> getApplicationById(@PathVariable Long id){
		ApplicationDto applicationDto = applicationService.getApplicationById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Application fetched successfully"
				,applicationDto, LocalDateTime.now()));
	}	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ApplicationDto>> updateApplication(@PathVariable Long id, @RequestBody ApplicationDto dto){
		ApplicationDto applicationDto = applicationService.updateApplication(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Application updated successfully"
				,applicationDto, LocalDateTime.now()));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteApplication(@PathVariable Long id){
		applicationService.deleteApplication(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Application deleted Successfully",
							null, LocalDateTime.now()));
	}
	
	

	@PutMapping("/{id}/status")
	public ResponseEntity<ApiResponse<ApplicationDto>> updateStatus(
	        @PathVariable Long id,
	        @RequestParam ApplicationStatus status) {

	    ApplicationDto applicationDto = applicationService.updateStatus(id, status);
	    
	    return ResponseEntity.ok(new ApiResponse<>(true, "Application updated successfully"
				,applicationDto, LocalDateTime.now()));
	}

	
	@GetMapping("/candidate/{candidateId}")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>> > getApplicationsByCandidateId(@RequestParam Long candidateId,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize) {
	    
		PageResponse<ApplicationDto> pageResponse = 
				applicationService.getApplicationsByCandidateId(candidateId, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	

	@GetMapping("/job/{jobId}")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>>> getApplicationsByJobId(@RequestParam Long jobId,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize) {
		
	    PageResponse<ApplicationDto> pageResponse = 
	    				applicationService.getApplicationsByJobId(jobId, pageNo, pageSize);
	    
	    ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();
	    
	    response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());
	
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/status")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>> > getApplicationByStatus(@RequestParam ApplicationStatus status,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<ApplicationDto> pageResponse = 
				applicationService.getApplicationByStatus(status, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Users fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/candidate_name")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>> > getApplicationByCandidateName(@RequestParam String keyword,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize){
		PageResponse<ApplicationDto> pageResponse = 
				applicationService.getApplicationByCandidateName(keyword, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Candidate fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/job_title")
	public ResponseEntity<ApiResponse<PageResponse<ApplicationDto>> > getApplicationByJobTitle(@RequestParam String keyword,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize){
		PageResponse<ApplicationDto> pageResponse = 
				applicationService.getApplicationByJobTitle(keyword, pageNo, pageSize);

		ApiResponse<PageResponse<ApplicationDto>> response = new ApiResponse<>();

		response.setSuccess(true);
		response.setMessage("Job fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
}







