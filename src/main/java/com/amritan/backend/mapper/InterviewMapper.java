package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.entity.Application;
import com.amritan.backend.entity.Interview;

@Component
public class InterviewMapper {

	public Interview mapToEntity(InterviewDto dto) {
		
		Interview interview = new Interview();
		
		interview.setId(dto.getId());
		interview.setInterviewDate(dto.getInterviewDate());
		interview.setMode(dto.getMode());
		interview.setInterviewer(dto.getInterviewer());
		interview.setStatus(dto.getStatus());
		
		if(dto.getApplicationId()!=null) {
			Application application = new Application();
			application.setId(dto.getApplicationId());
			interview.setApplication(application);
		}
		
		return interview;
	}
	
	
    public InterviewDto mapToDto(Interview interview) {

        InterviewDto dto = new InterviewDto();

        dto.setId(interview.getId());
        dto.setInterviewDate(interview.getInterviewDate());
        dto.setMode(interview.getMode());
        dto.setInterviewer(interview.getInterviewer());
        dto.setStatus(interview.getStatus());

        if(interview.getApplication()!=null) {
            dto.setApplicationId(interview.getApplication().getId());
        }

        return dto;
    }


}