package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.ApplicationDto;
import com.amritan.backend.entity.Application;

@Component
public class ApplicationMapper {

	public static Application mapToEntity(ApplicationDto dto) {
		Application application = new Application();
		
		application.setId(dto.getId());
		
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
