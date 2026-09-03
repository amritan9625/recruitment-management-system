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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
@Tag(
        name = "Dashboard APIs",
        description = "APIs for recruitment dashboard data"
)
public class DashboardController {

	private final DashboardService dashboardService;
	
	@GetMapping
	@Operation(
	        summary = "Get dashboard data",
	        description = "Fetches aggregated recruitment dashboard information"
	)
	public ResponseEntity<DashboardDto> getDashboard(){
		
		return ResponseEntity.ok(dashboardService.getDashboard());
	}
	
	
	@GetMapping("/recent-candidates")
	@Operation(
	        summary = "Get recent candidates",
	        description = "Fetches recently added candidates"
	)
	public ResponseEntity<List<CandidateDto>> getRecentCandidates() {

	    return ResponseEntity.ok(dashboardService.getRecentCandidates());
	}
	
	
	@GetMapping("/recent-jobs")
	@Operation(
	        summary = "Get recent jobs",
	        description = "Fetches recently added jobs"
	)
	public ResponseEntity<List<JobDto>> getRecentJobs() {

	    return ResponseEntity.ok(dashboardService.getRecentJobs());
	}
	
	
	@GetMapping("/recent-applications")
	@Operation(
	        summary = "Get recent applications",
	        description = "Fetches recently created applications"
	)
	public ResponseEntity<List<ApplicationDto>> getRecentApplications() {

	    return ResponseEntity.ok(dashboardService.getRecentApplications());
	}
	
	
}

