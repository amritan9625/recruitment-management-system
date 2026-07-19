package com.amritan.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.entity.Application;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.entity.Job;
import com.amritan.backend.enums.ApplicationStatus;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.ApplicationMapper;
import com.amritan.backend.repository.ApplicationRepository;
import com.amritan.backend.repository.CandidateRepository;
import com.amritan.backend.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
public class ApplicationServiceImpl implements ApplicationService{
	
	private ApplicationRepository applicationRepository;
	@Autowired
	public void setApplicationRepository(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}
	
	private CandidateRepository candidateRepository;
	@Autowired
	public void setCandidateRepository(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}
	
	private JobRepository jobRepository;
	@Autowired
	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
	
	private ApplicationMapper applicationMapper;
	@Autowired
	public void setApplicationMapper(ApplicationMapper applicationMapper) {
		this.applicationMapper = applicationMapper;
	}

	
	
	@Override
	public ApplicationDto createApplication(ApplicationDto dto) {
		
		Candidate candidate = candidateRepository.findById(dto.getCandidateId())
				.orElseThrow(() ->
							new ResourceNotFoundException("Candidate not found with id : "
									+dto.getCandidateId()));
		
		Job job = jobRepository.findById(dto.getJobId())
				.orElseThrow(() ->
						new ResourceNotFoundException("Job not found with id : "
								+dto.getJobId()));
		
		Application application = applicationMapper.mapToEntity(dto);
		
		application.setCandidate(candidate);
		application.setJob(job);
		application.setStatus(ApplicationStatus.APPLIED);
		
		Application savedApplication = applicationRepository.save(application);
		
		return applicationMapper.mapToDto(savedApplication);		
	}

	@Override
	public List<ApplicationDto> getAllApplications() {
		List<Application> applications = applicationRepository.findAll();
		
		return applications.stream()
				.map(applicationMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ApplicationDto getApplicationById(Long id) {
		Application application = applicationRepository.findById(id)
								.orElseThrow(() ->
								new ResourceNotFoundException("Application not found with id : "+id));
		
		return applicationMapper.mapToDto(application);
	}

	@Override
	public ApplicationDto updateApplication(Long id, ApplicationDto dto) {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Application not found with id : "+id));

		Candidate candidate = candidateRepository.findById(dto.getCandidateId())
				.orElseThrow(() ->
				new ResourceNotFoundException("Candidate not found with id : "
						+dto.getCandidateId()));

		Job job = jobRepository.findById(dto.getJobId())
				.orElseThrow(() ->
				new ResourceNotFoundException("Job not found with id : "
					+dto.getJobId()));
		
		application.setCandidate(candidate);
		application.setJob(job);
		
		if (dto.getStatus() != null) {
                    application.setStatus(dto.getStatus());
        }
		
		Application savedApplication = applicationRepository.save(application);
		
		return applicationMapper.mapToDto(savedApplication);
	}

	@Override
	public void deleteApplication(Long id) {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Application not found with id : "+id));

		applicationRepository.delete(application);
	}

}
