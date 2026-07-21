package com.amritan.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amritan.backend.dto.OfferDto;
import com.amritan.backend.entity.Candidate;
import com.amritan.backend.entity.Job;
import com.amritan.backend.entity.Offer;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.OfferMapper;
import com.amritan.backend.repository.CandidateRepository;
import com.amritan.backend.repository.JobRepository;
import com.amritan.backend.repository.OfferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService{
	
	private final OfferRepository offerRepository;

	private final CandidateRepository candidateRepository;
	
	private final JobRepository jobRepository;
	
	
	public OfferDto createOffer(OfferDto offerDto) {
		Offer offer = OfferMapper.mapToEntity(offerDto);

        Offer savedOffer = offerRepository.save(offer);

        return OfferMapper.mapToDto(savedOffer);
	}

	@Override
	public List<OfferDto> getAllOffers() {
		List<Offer> offers = offerRepository.findAll();
		
		return offers.stream()
				.map(OfferMapper::mapToDto)
				.toList();
	}
	
	@Override
	public OfferDto getOfferById(Long id) {
		Offer offer = offerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
		
		return OfferMapper.mapToDto(offer);
	}

	@Override
	public OfferDto updateOffer(Long id, OfferDto offerDto) {
		Candidate candidate = candidateRepository.findById(offerDto.getCandidateId())
				.orElseThrow(() ->
				new ResourceNotFoundException("Candidate not found with id : "
						+offerDto.getCandidateId()));
		
		Job job = jobRepository.findById(offerDto.getJobId())
				.orElseThrow(() ->
				new ResourceNotFoundException("Job not found with id : "
						+offerDto.getJobId()));
		
		Offer offer = offerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
		
		offer.setSalary(offerDto.getSalary());
		offer.setJoiningDate(offerDto.getJoiningDate());
		offer.setStatus(offerDto.getStatus());
		
	    offer.setCandidate(candidate);
	    
	    offer.setJob(job);
		
		Offer savedOffer = offerRepository.save(offer);
		
		return OfferMapper.mapToDto(savedOffer);
	}

	@Override
	public void deleteOffer(Long id) {
		Offer offer = offerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));;
		
		offerRepository.delete(offer);
	}
}
