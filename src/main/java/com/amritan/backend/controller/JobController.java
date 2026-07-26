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

import com.amritan.backend.dto.JobDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

	private JobService jobService;
	
	@Autowired
	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
	@PostMapping
	public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto jobtDto){
		JobDto savedJobDto = jobService.createJob(jobtDto);
		
		return new ResponseEntity<>(
				savedJobDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<JobDto>> getAllJobs( @RequestParam(defaultValue = "0") int pageNo,
											@RequestParam(defaultValue = "10") int pageSize,
											@RequestParam(defaultValue = "id") String sortBy,
											@RequestParam(defaultValue = "asc") String sortDir) {

        return ResponseEntity.ok(
                jobService.getAllJobs(pageNo, pageSize, sortBy, sortDir) );
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDto> getJobById(@PathVariable Long id){
		JobDto dto = jobService.getJobById(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<JobDto> updateJob(@PathVariable Long id, @RequestBody JobDto dto){
		JobDto updateDto = jobService.updateJob(id, dto);
		
		return ResponseEntity.ok(updateDto);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable Long id){
		jobService.deleteJob(id);
		
		return ResponseEntity.ok("Job Deleted Successfully");
	}
	
	
	 @GetMapping("/search")
	    public ResponseEntity<PageResponse<JobDto>> searchJobs( @RequestParam String keyword,
	    													@RequestParam(defaultValue = "0") int pageNo,
	    													@RequestParam(defaultValue = "10") int pageSize) {

	        return ResponseEntity.ok(
	                jobService.searchJobs( keyword, pageNo, pageSize) );
	    }

	    @GetMapping("/status/{status}")
	    public ResponseEntity<PageResponse<JobDto>> getJobsByStatus( @PathVariable String status,
	    													@RequestParam(defaultValue = "0") int pageNo,
	    													@RequestParam(defaultValue = "10") int pageSize) {

	        return ResponseEntity.ok(
	                jobService.getJobsByStatus( status, pageNo, pageSize) );
	    }

	    @GetMapping("/location")
	    public ResponseEntity<PageResponse<JobDto>> getJobsByLocation( @RequestParam String location,
	    														@RequestParam(defaultValue = "0") int pageNo,
	    														@RequestParam(defaultValue = "10") int pageSize) {

	        return ResponseEntity.ok(
	                jobService.getJobsByLocation( location, pageNo, pageSize) );
	    }
	
}
