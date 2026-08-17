package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.InterviewDto;

public interface InterviewService {

	InterviewDto createInterview(InterviewDto dto);

    List<InterviewDto> getAllInterviews();

    InterviewDto getInterviewById(Long id);

    InterviewDto updateInterview(Long id, InterviewDto dto);

    void deleteInterview(Long id);
}