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
import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.InterviewStatus;
import com.amritan.backend.service.InterviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
public class InterviewController {
	
	private final InterviewService interviewService;
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<InterviewDto>> createInterview(@Valid @RequestBody InterviewDto interviewDto){
		InterviewDto dto = interviewService.createInterview(interviewDto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "Interview created successfully",
				dto, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>> > getAllInterviews(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		PageResponse<InterviewDto> pageResponse = interviewService.getAllInterviews(pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Interview fetched successfully");
	    response.setData(pageResponse);
	    response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<InterviewDto>> getInterviewById(@PathVariable Long id){
		InterviewDto interviewDto = interviewService.getInterviewById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Interview fetched successfully",
				interviewDto, LocalDateTime.now()) );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<InterviewDto>> updateInterview(@PathVariable Long id, @RequestBody InterviewDto dto){
		InterviewDto interviewDto = interviewService.updateInterview(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Interview fetched successfully",
				interviewDto, LocalDateTime.now()) );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteInterview(@PathVariable Long id){
		interviewService.deleteInterview(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Interview fetched successfully",
				null, LocalDateTime.now()) );
	}
	
	
	
	@GetMapping("/status")
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>> >getInterviewByStatus(@RequestParam InterviewStatus status,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<InterviewDto> pageResponse = interviewService.getInterviewByStatus(status, pageNo, pageSize);
		
		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Interview fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/candidate_name")
	public ResponseEntity<ApiResponse<PageResponse<InterviewDto>> >getInterviewer(@RequestParam String keyword,
					@RequestParam(defaultValue = "0") int pageNo,
					@RequestParam(defaultValue = "10") int pageSize){
		
		PageResponse<InterviewDto> pageResponse = interviewService.getInterviewer(keyword, pageNo, pageSize);
		
		ApiResponse<PageResponse<InterviewDto>> response = new ApiResponse<>();
		
		response.setSuccess(true);
		response.setMessage("Interviewer fetched successfully");
		response.setData(pageResponse);
		response.setTimestamp(LocalDateTime.now());

		return ResponseEntity.ok(response);
	}
	
}