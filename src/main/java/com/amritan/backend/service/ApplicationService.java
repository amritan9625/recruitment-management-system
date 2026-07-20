package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.enums.ApplicationStatus;

public interface ApplicationService {

	ApplicationDto createApplication(ApplicationDto dto);

	List<ApplicationDto> getAllApplications();

	ApplicationDto getApplicationById(Long id);
	
	List<ApplicationDto> getApplicationsByJob(Long jobId);
	
	List<ApplicationDto> getApplicationsByCandidate(Long candidateId);
	
	ApplicationDto updateStatus(Long id, ApplicationStatus status);

    ApplicationDto updateApplication(Long id,
                                     ApplicationDto dto);

    void deleteApplication(Long id);
}
