package com.amritan.backend.service;

import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.InterviewStatus;

public interface InterviewService {

	InterviewDto createInterview(InterviewDto dto);

    PageResponse<InterviewDto> getAllInterviews(int pageNo, int pageSize,
			String sortBy, String sortDir);

    InterviewDto getInterviewById(Long id);

    InterviewDto updateInterview(Long id, InterviewDto dto);

    void deleteInterview(Long id);
    
    PageResponse<InterviewDto> getInterviewByStatus(InterviewStatus status, int pageNo, int pageSize);
    
    PageResponse<InterviewDto> getInterviewer(String keyword, int pageNo, int pageSize);
}