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

import com.amritan.backend.dto.JobDto;
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
	public ResponseEntity<List<JobDto>> getAllJobs(){
		List<JobDto> jobDtos = jobService.getAllJobs();
		
		return ResponseEntity.ok(jobDtos);
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
	
	
}
