package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.entity.Candidate;

@Component
public class CandidateMapper {

	
	public static Candidate mapToEntity(CandidateDto dto) {
		Candidate candidate = new Candidate();
		
		candidate.setId(dto.getId());
		candidate.setFirstName(dto.getFirstName());
		candidate.setLastName(dto.getLastName());
		candidate.setEmail(dto.getEmail());
		candidate.setPhone(dto.getPhone());
		candidate.setSkills(dto.getSkills());
		candidate.setExperience(dto.getExperience());
		candidate.setResumeUrl(dto.getResumeUrl());
		
		return candidate;
	}
	
	public static CandidateDto mapToDto(Candidate candidate) {
		CandidateDto dto = new CandidateDto();
		
		dto.setId(candidate.getId());
		dto.setFirstName(candidate.getFirstName());
		dto.setLastName(candidate.getLastName());
		dto.setEmail(candidate.getEmail());
		dto.setPhone(candidate.getPhone());
		dto.setSkills(candidate.getSkills());
		dto.setExperience(candidate.getExperience());
		dto.setResumeUrl(candidate.getResumeUrl());
		
		return dto;
	}
}
