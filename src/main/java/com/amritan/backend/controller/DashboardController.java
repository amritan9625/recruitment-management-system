package com.amritan.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.DashboardDto;
import com.amritan.backend.dto.JobDto;
import com.amritan.backend.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

	private final DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<DashboardDto> getDashboard(){
		
		return ResponseEntity.ok(dashboardService.getDashboard());
	}
	
	
	@GetMapping("/recent-candidates")
	public ResponseEntity<List<CandidateDto>> getRecentCandidates() {

	    return ResponseEntity.ok(dashboardService.getRecentCandidates());
	}
	
	
	@GetMapping("/recent-jobs")
	public ResponseEntity<List<JobDto>> getRecentJobs() {

	    return ResponseEntity.ok(dashboardService.getRecentJobs());
	}
	
	
	@GetMapping("/recent-applications")
	public ResponseEntity<List<ApplicationDto>> getRecentApplications() {

	    return ResponseEntity.ok(dashboardService.getRecentApplications());
	}
	
	
}

