package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.PageResponse;
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
	
	
	private String getLoggedInEmail() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    return authentication.getName();
	}
	
	private boolean isCandidate() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    return authentication.getAuthorities()
	            .stream()
	            .anyMatch(authority ->
	                    authority.getAuthority().equals("ROLE_CANDIDATE"));
	}
	
	private void validateCandidateOwnership(Long candidateId) {

	    if (!isCandidate()) {
	        return;
	    }

	    Candidate candidate = candidateRepository.findById(candidateId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Candidate not found with id : " + candidateId));

	    String loggedInEmail = getLoggedInEmail();

	    if (!loggedInEmail.equalsIgnoreCase(candidate.getEmail())) {
	        throw new AccessDeniedException("You are not allowed to access another candidate's data");
	    }
	}
	
	
	@Override
	public ApplicationDto createApplication(ApplicationDto dto) {
		
		Candidate candidate = candidateRepository.findById(dto.getCandidateId())
				.orElseThrow(() ->
							new ResourceNotFoundException("Candidate not found with id : "
									+dto.getCandidateId()));
		
		// Protecting createApplication
		if (isCandidate()) {
		    String loggedInEmail = getLoggedInEmail();

		    if (!loggedInEmail.equalsIgnoreCase(candidate.getEmail())) {
		        throw new AccessDeniedException("You cannot create an application for another candidate");
		    }
		}
		
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
	public PageResponse<ApplicationDto> getAllApplications(int pageNo, int pageSize
									, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending()
						: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository.findAll(pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public ApplicationDto getApplicationById(Long id) {
		Application application = applicationRepository.findById(id)
								.orElseThrow(() ->
								new ResourceNotFoundException("Application not found with id : "+id));
		
		if (isCandidate()) {
	        String loggedInEmail = getLoggedInEmail();

	        String candidateEmail = application.getCandidate().getEmail();

	        if (!loggedInEmail.equalsIgnoreCase(candidateEmail)) {
	            throw new AccessDeniedException("You are not allowed to access this application");
	        }
	    }
		
		return ApplicationMapper.mapToDto(application);
	}
	
	
	
	@Override
	public ApplicationDto updateStatus(Long id, ApplicationStatus status) {
	    Application application = applicationRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id:"+id));
	    
	    if (isCandidate()) {
	        String loggedInEmail = getLoggedInEmail();

	        if (!loggedInEmail.equalsIgnoreCase(application.getCandidate().getEmail())) {
	            throw new AccessDeniedException("You are not allowed to update this application");
	        }
	    }

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
		if (isCandidate()) {
		    String loggedInEmail = getLoggedInEmail();
		    if (!loggedInEmail.equalsIgnoreCase(
		            application.getCandidate().getEmail())) {
		        throw new AccessDeniedException("You are not allowed to delete this application");
		    }
		}
		
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

	

	@Override
	public PageResponse<ApplicationDto> getApplicationsByCandidateId(Long candidateId, int pageNo, int pageSize) {
		
		validateCandidateOwnership(candidateId);
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository.findByCandidateId(candidateId, pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public PageResponse<ApplicationDto> getApplicationsByJobId(Long jobId, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository.findByJobId(jobId, pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public PageResponse<ApplicationDto> getApplicationByStatus(ApplicationStatus status, int pageNo, int pageSize) {
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository.findByStatus(status, pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public PageResponse<ApplicationDto> getApplicationByCandidateName(String keyword, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository
								.findByCandidateFirstNameContainingIgnoreCaseOrCandidateLastNameContainingIgnoreCase(keyword, keyword, pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

	@Override
	public PageResponse<ApplicationDto> getApplicationByJobTitle(String keyword, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Application> page = applicationRepository.findByJobTitleContainingIgnoreCase(keyword, pageable);
		
		List<ApplicationDto> content = page.getContent()
				.stream()
				.map(ApplicationMapper::mapToDto)
				.toList();
		
		return new PageResponse<>(content, page.getNumber()
				,page.getSize(), page.getTotalElements()
				, page.getTotalPages(), page.isLast());
	}

}