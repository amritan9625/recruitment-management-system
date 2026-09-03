package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.entity.Application;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.entity.Job;

@Component
public class ApplicationMapper {

	public static Application mapToEntity(ApplicationDto dto) {

	    Application application = new Application();

	    application.setId(dto.getId());
	    application.setStatus(dto.getStatus());

	    if (dto.getCandidateId() != null) {
	        Candidate candidate = new Candidate();
	        candidate.setId(dto.getCandidateId());
	        
	        application.setCandidate(candidate);
	    }

	    if (dto.getJobId() != null) {
	        Job job = new Job();
	        job.setId(dto.getJobId());
	        
	        application.setJob(job);
	    }

	    return application;
	}
	
	public static ApplicationDto mapToDto(Application application) {
		ApplicationDto dto = new ApplicationDto();
		
		dto.setId(application.getId());
		dto.setCandidateId(application.getCandidate().getId());
		dto.setJobId(application.getJob().getId());
		dto.setStatus(application.getStatus());
		
		return dto;
	}
}
