package com.amritan.backend.service.impl;

import com.amritan.backend.mapper.ApplicationMapper;
import com.amritan.backend.mapper.CandidateMapper;
import com.amritan.backend.mapper.JobMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.DashboardDto;
import com.amritan.backend.dto.JobDto;
import com.amritan.backend.repository.ApplicationRepository;
import com.amritan.backend.repository.CandidateRepository;
import com.amritan.backend.repository.InterviewRepository;
import com.amritan.backend.repository.JobRepository;
import com.amritan.backend.repository.OfferRepository;
import com.amritan.backend.repository.UserRepository;
import com.amritan.backend.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{

	private final UserRepository userRepository;

	private final CandidateRepository candidateRepository;

	private final JobRepository jobRepository;

	private final ApplicationRepository applicationRepository;

	private final InterviewRepository interviewRepository;

	private final OfferRepository offerRepository;

	@Override
	public DashboardDto getDashboard() {
		DashboardDto dto = new DashboardDto();

	    dto.setTotalUsers(userRepository.count());

	    dto.setTotalCandidates(candidateRepository.count());

	    dto.setTotalJobs(jobRepository.count());

	    dto.setTotalApplications(applicationRepository.count());

	    dto.setTotalInterviews(interviewRepository.count());

	    dto.setTotalOffers(offerRepository.count());

	    return dto;
	}

	@Override
	public List<CandidateDto> getRecentCandidates() {
		
		return candidateRepository.findTop5ByOrderByIdDesc()
	            .stream()
	            .map(CandidateMapper::mapToDto)
	            .toList();
	}

	@Override
	public List<JobDto> getRecentJobs() {
		return jobRepository.findTop5ByOrderByIdDesc()
	            .stream()
	            .map(JobMapper::mapToDto)
	            .toList();
	}

	@Override
	public List<ApplicationDto> getRecentApplications() {

	    return applicationRepository.findTop5ByOrderByIdDesc()
	            .stream()
	            .map(ApplicationMapper::mapToDto)
	            .toList();
	}
	
	
}
