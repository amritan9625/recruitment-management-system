package com.amritan.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.CandidateMapper;
import com.amritan.backend.repository.CandidateRepository;

@Service
public class CandidateServiceImpl implements CandidateService{

	private CandidateRepository candidateRepository;
	
	@Autowired
	public void setCandidateRepository(CandidateRepository candidateRepository) {
		this.candidateRepository = candidateRepository;
	}
	
	
	
	@Override
	public CandidateDto createCandidate(CandidateDto candidateDto) {
		Candidate candidate = CandidateMapper.mapToEntity(candidateDto);
		Candidate savedCandidate = candidateRepository.save(candidate);
		
		return CandidateMapper.mapToDto(savedCandidate);
	}

	@Override
	public List<CandidateDto> getAllCandidates() {
		List<Candidate> candidates = candidateRepository.findAll();
		
		return candidates.stream()
				.map(CandidateMapper::mapToDto)
				.toList();
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
		
		return CandidateMapper.mapToDto(candidate);
	}

	@Override
	public void deleteCandidate(Long id) {
		Candidate candidate = candidateRepository.findById(id)
				.orElseThrow(() ->
				new ResourceNotFoundException(
						"Candidate not found with the id : "+id));
		
		candidateRepository.delete(candidate);
		
	}

}
