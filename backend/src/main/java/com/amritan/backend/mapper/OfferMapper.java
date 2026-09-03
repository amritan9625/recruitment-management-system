package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.OfferDto;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.entity.Job;
import com.amritan.backend.entity.Offer;

@Component
public class OfferMapper {

	public static Offer mapToEntity(OfferDto dto) {
		
		Offer offer = new Offer();
		
		offer.setId(dto.getId());
		offer.setSalary(dto.getSalary());
		offer.setJoiningDate(dto.getJoiningDate());
		offer.setStatus(dto.getStatus());
		
		if (dto.getCandidateId() != null) {
			Candidate candidate = new Candidate();
			candidate.setId(dto.getCandidateId());
			offer.setCandidate(candidate);
		}
		
		if (dto.getJobId() != null) {
			Job job = new Job();
			job.setId(dto.getJobId());
			offer.setJob(job);
		}
		
		return offer;
	}
	
	public static OfferDto mapToDto(Offer offer) {
		OfferDto dto = new OfferDto();

	    dto.setId(offer.getId());
	    dto.setSalary(offer.getSalary());
	    dto.setJoiningDate(offer.getJoiningDate());
	    dto.setStatus(offer.getStatus());

	    if (offer.getCandidate() != null) {
	        dto.setCandidateId(offer.getCandidate().getId());
	    }

	    if (offer.getJob() != null) {
	        dto.setJobId(offer.getJob().getId());
	    }

	    return dto;
	 }

}
