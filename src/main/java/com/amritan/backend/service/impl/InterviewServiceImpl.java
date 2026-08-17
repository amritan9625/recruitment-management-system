package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.InterviewDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.entity.Application;
import com.amritan.backend.entity.Interview;
import com.amritan.backend.enums.InterviewStatus;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.InterviewMapper;
import com.amritan.backend.repository.ApplicationRepository;
import com.amritan.backend.repository.InterviewRepository;
import com.amritan.backend.service.InterviewService;

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
	public PageResponse<InterviewDto> getAllInterviews(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")
					? Sort.by(sortBy).ascending()
							: Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Interview> page = interviewRepository.findAll(pageable);
		
		List<InterviewDto> content = page.getContent()
								.stream()
								.map(interviewMapper::mapToDto)
								.toList();
		
		return new PageResponse<>( content,
				page.getNumber(),
				page.getSize(),
				page.getTotalElements(),
	    			page.getTotalPages(),
	    			page.isLast() );
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
		Interview interview = interviewRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Interview not found with id : "+id));
		
		Application application = applicationRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException("Application not found with id : "+id));
		
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

	

	@Override
	public PageResponse<InterviewDto> getInterviewByStatus(InterviewStatus status, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Interview> page = interviewRepository.findByStatus(status, pageable);
		
		List<InterviewDto> content = page.getContent()
								.stream()
								.map(interviewMapper::mapToDto)
								.toList();
		
		return new PageResponse<>( content,
	    		page.getNumber(),
	    		page.getSize(),
	    		page.getTotalElements(),
	    		page.getTotalPages(),
	    		page.isLast() );
	}

	@Override
	public PageResponse<InterviewDto> getInterviewer(String keyword, int pageNo, int pageSize) {
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Interview> page = interviewRepository.findByInterviewerContainingIgnoreCase(keyword, pageable);
		
		List<InterviewDto> content = page.getContent()
								.stream()
								.map(interviewMapper::mapToDto)
								.toList();
		
		return new PageResponse<>( content,
	    		page.getNumber(),
	    		page.getSize(),
	    		page.getTotalElements(),
	    		page.getTotalPages(),
	    		page.isLast() );
	}
	
	
	
}