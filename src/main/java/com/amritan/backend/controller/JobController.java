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
import com.amritan.backend.dto.JobDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

	private final JobService jobService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<JobDto> > createJob(@Valid @RequestBody JobDto jobtDto){
		JobDto jobDto = jobService.createJob(jobtDto);
		
		return new ResponseEntity<>(new ApiResponse<>(true, "Job created successfully",
									jobDto, LocalDateTime.now()), HttpStatus.CREATED);
	}
	
	
	
	@GetMapping
	public ResponseEntity<ApiResponse<PageResponse<JobDto>> > getAllJobs( @RequestParam(defaultValue = "0") int pageNo,
											@RequestParam(defaultValue = "10") int pageSize,
											@RequestParam(defaultValue = "id") String sortBy,
											@RequestParam(defaultValue = "asc") String sortDir) {
		
		PageResponse<JobDto> pageResponse =  jobService.getAllJobs(pageNo, pageSize, sortBy, sortDir);
		
		ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();

	    response.setSuccess(true);
	    response.setMessage("Jobs fetched successfully");
	    response.setData(pageResponse);
	    response.setTimestamp(LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<JobDto>> getJobById(@PathVariable Long id){
		JobDto jobDto = jobService.getJobById(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Job fetched successfully",
								jobDto, LocalDateTime.now()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<JobDto>> updateJob(@PathVariable Long id, @RequestBody JobDto dto){
		JobDto updateDto = jobService.updateJob(id, dto);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Job updated successfully", 
								updateDto, LocalDateTime.now()));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<String>> deleteJob(@PathVariable Long id){
		jobService.deleteJob(id);
		
		return ResponseEntity.ok(new ApiResponse<>(true, "Job Deleted Successfully",
				null, LocalDateTime.now()));
	}
	
	

	    @GetMapping("/status/{status}")
	    public ResponseEntity<ApiResponse<PageResponse<JobDto>>> getJobsByStatus( @PathVariable String status,
	    													@RequestParam(defaultValue = "0") int pageNo,
	    													@RequestParam(defaultValue = "10") int pageSize) {

	    		PageResponse<JobDto> pageResponse = 
					jobService.getJobsByStatus(status, pageNo, pageSize);
			ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();
		
			response.setSuccess(true);
			response.setMessage("User fetched successfully");
			response.setData(pageResponse);
			response.setTimestamp(LocalDateTime.now());
		
			return ResponseEntity.ok(response);
			
	    }

	    @GetMapping("/location")
	    public ResponseEntity<ApiResponse<PageResponse<JobDto>>> getJobsByLocation( @RequestParam String location,
	    														@RequestParam(defaultValue = "0") int pageNo,
	    														@RequestParam(defaultValue = "10") int pageSize) {

	    		PageResponse<JobDto> pageResponse = 
					jobService.getJobsByLocation(location, pageNo, pageSize);
	    		
			ApiResponse<PageResponse<JobDto>> response = new ApiResponse<>();
		
			response.setSuccess(true);
			response.setMessage("User fetched successfully");
			response.setData(pageResponse);
			response.setTimestamp(LocalDateTime.now());
			
			return ResponseEntity.ok(response);
	    }
	
}
