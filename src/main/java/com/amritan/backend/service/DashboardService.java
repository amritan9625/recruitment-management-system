package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.DashboardDto;
import com.amritan.backend.dto.JobDto;

public interface DashboardService {

	DashboardDto getDashboard();
	
	List<CandidateDto> getRecentCandidates();

	List<JobDto> getRecentJobs();
	
	List<ApplicationDto> getRecentApplications();
}
