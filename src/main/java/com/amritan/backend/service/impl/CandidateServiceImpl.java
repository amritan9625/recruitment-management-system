package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.enums.CandidateStatus;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.CandidateMapper;
import com.amritan.backend.repository.CandidateRepository;
import com.amritan.backend.service.CandidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService{

	private final CandidateRepository candidateRepository;
	
	
	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {
		Candidate candidate = CandidateMapper.mapToEntity(candidateDto);
		Candidate savedCandidate = candidateRepository.save(candidate);
		
		return CandidateMapper.mapToDto(savedCandidate);
	}
	

	@Override
	public PageResponse<CandidateDto> getAllCandidates(int pageNo, int pageSize
								, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")
		        ? Sort.by(sortBy).ascending()
		        : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Candidate> page = candidateRepository.findAll(pageable);
		
		List<CandidateDto> content = page.getContent()
		        .stream()
		        .map(CandidateMapper::mapToDto)
		        .toList();
		
		return new PageResponse<>(
		        content,
		        page.getNumber(),
		        page.getSize(),
		        page.getTotalElements(),
		        page.getTotalPages(),
		        page.isLast()
		);
	}
	
	@Override
	public CandidateDto getCandidateById(Long id) {
		Candidate candidate = candidateRepository.findById(id)
							.orElseThrow(() ->
							new ResourceNotFoundException(
									"Candidate not found with the id : "+id));
		return CandidateMapper.mapToDto(candidate);
	}
	
	@Override
	public CandidateDto updateCandidate(Long id, CandidateDto dto) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException(
						"Candidate not found with the id : "+id));
		
		candidate.setFirstName(dto.getFirstName());
		candidate.setLastName(dto.getLastName());
		candidate.setEmail(dto.getEmail());
		candidate.setPhone(dto.getPhone());
		candidate.setSkills(dto.getSkills());
		candidate.setExperience(dto.getExperience());
		candidate.setResumeUrl(dto.getResumeUrl());
		candidate.setStatus(dto.getStatus());
		
		Candidate updateCandidate = candidateRepository.save(candidate);
		
		return CandidateMapper.mapToDto(updateCandidate);
	}

	@Override
	public void deleteCandidate(Long id) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException(
						"Candidate not found with the id : "+id));
		
		candidateRepository.delete(candidate);
		
	}


	@Override
	public PageResponse<CandidateDto> searchCandidates(String keyword, int pageNo, int pageSize) {
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Candidate> page = candidateRepository.findByFirstNameContainingIgnoreCase(
		                keyword,
		                pageable
		        );
		
		List<CandidateDto> content = page.getContent()
		        .stream()
		        .map(CandidateMapper::mapToDto)
		        .toList();
		
		return new PageResponse<>(
		        content,
		        page.getNumber(),
		        page.getSize(),
		        page.getTotalElements(),
		        page.getTotalPages(),
		        page.isLast()
		);
	}

	@Override
	public PageResponse<CandidateDto> getCandidatesByStatus(CandidateStatus status, int pageNo, int pageSize) {
		
		Sort sort = Sort.by("id").ascending();
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Candidate> page = candidateRepository.findByStatus(status, pageable);
		
		List<CandidateDto> content = page.getContent()
		        .stream()
		        .map(CandidateMapper::mapToDto)
		        .toList();
		
		return new PageResponse<>(
		        content,
		        page.getNumber(),
		        page.getSize(),
		        page.getTotalElements(),
		        page.getTotalPages(),
		        page.isLast() );
	}

}
