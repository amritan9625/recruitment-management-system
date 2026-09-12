package com.amritan.backend.service;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.ApplicationStatus;

public interface ApplicationService {

	ApplicationDto createApplication(ApplicationDto dto);
	
	ApplicationDto applyForJob(Long jobId);

	PageResponse<ApplicationDto> getAllApplications(int pageNo, int pageSize,
			String sortBy, String sortDir);
	
	PageResponse<ApplicationDto> getMyApplications(
	        int pageNo,
	        int pageSize
	);

	ApplicationDto getApplicationById(Long id);
	
	ApplicationDto updateStatus(Long id, ApplicationStatus status);
	
	ApplicationDto updateApplication(Long id, ApplicationDto dto);
	
	void deleteApplication(Long id);

	PageResponse<ApplicationDto> getApplicationsByCandidateId(Long candidateId, int pageNo, int pageSize);

    PageResponse<ApplicationDto> getApplicationsByJobId(Long jobId, int pageNo, int pageSize);
    
    PageResponse<ApplicationDto> getApplicationByStatus(ApplicationStatus status, int pageNo, int pageSize);
    
    PageResponse<ApplicationDto> getApplicationByCandidateName(String keyword, int pageNo, int pageSize);
    
    PageResponse<ApplicationDto> getApplicationByJobTitle(String keyword, int pageNo, int pageSize);    
}
