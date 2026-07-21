package com.amritan.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.entity.Application;
import com.amritan.backend.entity.Interview;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.InterviewMapper;
import com.amritan.backend.repository.ApplicationRepository;
import com.amritan.backend.repository.InterviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService{
	
	private final InterviewRepository interviewRepository;
	
	private final ApplicationRepository applicationRepository;
	
	private final InterviewMapper interviewMapper;


	
	@Override
	public InterviewDto createInterview(InterviewDto dto) {
		Application application = applicationRepository.findById(dto.getApplicationId())
		        .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

		Interview interview = interviewMapper.mapToEntity(dto);
		interview.setApplication(application);

		Interview saved = interviewRepository.save(interview);

		return interviewMapper.mapToDto(saved);
	}
	
	@Override
	public List<InterviewDto> getAllInterviews() {
		List<Interview> interviews = interviewRepository.findAll();
		
		return interviews.stream()
				.map(interviewMapper::mapToDto)
				.collect(Collectors.toList());
	}
	

	@Override
	public InterviewDto getInterviewById(Long id) {
		Interview interview = interviewRepository.findById(id)
							.orElseThrow(() ->
							new ResourceNotFoundException("Interview not found with id : "+id));
		
		return interviewMapper.mapToDto(interview);
	}

	@Override
	public InterviewDto updateInterview(Long id, InterviewDto dto) {
		Application application = applicationRepository.findById(dto.getApplicationId())
		        .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Interview not found with id : "+id));
		
		
		interview.setInterviewDate(dto.getInterviewDate());
		interview.setMode(dto.getMode());
		interview.setInterviewer(dto.getInterviewer());
		interview.setStatus(dto.getStatus());
		interview.setApplication(application);
		
		Interview savedInterview = interviewRepository.save(interview);
		
		return interviewMapper.mapToDto(savedInterview);
	}

	@Override
	public void deleteInterview(Long id) {
		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Interview not found with id : "+id));
		
		interviewRepository.delete(interview);
	}
	
	
	
}
