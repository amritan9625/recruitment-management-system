package com.amritan.backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.amritan.backend.service.ApplicationService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService{
	
	private final ApplicationRepository applicationRepository;
	
	private final CandidateRepository candidateRepository;
	
	private final JobRepository jobRepository;
	
	
	
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
		
		Application application = ApplicationMapper.mapToEntity(dto);
		
		application.setCandidate(candidate);
		application.setJob(job);
		application.setStatus(ApplicationStatus.APPLIED);
		
		Application savedApplication = applicationRepository.save(application);
		
		return ApplicationMapper.mapToDto(savedApplication);		
	}

	@Override
	public List<ApplicationDto> getAllApplications() {
		List<Application> applications = applicationRepository.findAll();
		
		return applications.stream()
				.map(ApplicationMapper::mapToDto)
				.collect(Collectors.toList());
	}

	@Override
	public ApplicationDto getApplicationById(Long id) {
		Application application = applicationRepository.findById(id)
								.orElseThrow(() ->
								new ResourceNotFoundException("Application not found with id : "+id));
		
		return ApplicationMapper.mapToDto(application);
	}
	
	
	
	@Override
	public List<ApplicationDto> getApplicationsByJob(Long jobId) {

	    return applicationRepository.findByJobId(jobId)
	            .stream()
	            .map(ApplicationMapper::mapToDto)
	            .toList();

	}
	
	
	
	@Override
	public List<ApplicationDto> getApplicationsByCandidate(Long candidateId) {

	    return applicationRepository.findByCandidateId(candidateId)
	            .stream()
	            .map(ApplicationMapper::mapToDto)
	            .toList();

	}
	
	@Override
	public ApplicationDto updateStatus(Long id, ApplicationStatus status) {

	    Application application = applicationRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Application not found"));

	    application.setStatus(status);

	    Application saved = applicationRepository.save(application);

	    return ApplicationMapper.mapToDto(saved);

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
		
		return ApplicationMapper.mapToDto(savedApplication);
	}

	@Override
	public void deleteApplication(Long id) {
		Application application = applicationRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Application not found with id : "+id));

		applicationRepository.delete(application);
	}

}
