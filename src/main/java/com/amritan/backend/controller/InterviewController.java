package com.amritan.backend.controller;

import java.util.List;

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

import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.service.InterviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {
	
	private final InterviewService interviewService;
	
	
	@PostMapping
	public ResponseEntity<InterviewDto> createInterview(@Valid @RequestBody InterviewDto interviewDto){
		InterviewDto dto = interviewService.createInterview(interviewDto);
		
		return new ResponseEntity<>(
				dto, HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<List<InterviewDto>> getAllInterviews(){
		List<InterviewDto> interviewDtos = interviewService.getAllInterviews();
		
		return ResponseEntity.ok(interviewDtos);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<InterviewDto> getInterviewById(@PathVariable Long id){
		InterviewDto interviewDto = interviewService.getInterviewById(id);
		
		return ResponseEntity.ok(interviewDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<InterviewDto> updateInterview(@PathVariable Long id, @RequestBody InterviewDto dto){
		InterviewDto interviewDto = interviewService.updateInterview(id, dto);
		
		return ResponseEntity.ok(interviewDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteInterview(@PathVariable Long id){
		interviewService.deleteInterview(id);
		
		return ResponseEntity.ok("Interview deleted successfully");
	}
	
}