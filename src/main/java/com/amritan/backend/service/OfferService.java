package com.amritan.backend.service;

import com.amritan.backend.dto.OfferDto;
import com.amritan.backend.dto.PageResponse;

public interface OfferService {

	OfferDto createOffer(OfferDto offerDto);

	PageResponse<OfferDto> getAllOffers(int pageNo, int pageSize,
			String sortBy, String sortDir);

	OfferDto getOfferById(Long id);

	OfferDto updateOffer(Long id, OfferDto offerDto);

	void deleteOffer(Long id);
	
	PageResponse<OfferDto> getOfferBySalary(Double salary, int pageNo, int pageSize);
	
	PageResponse<OfferDto> getOfferByCandidateName(String keyword, int pageNo, int pageSize);
	
	PageResponse<OfferDto> getOfferByJobTitle(String keyword, int pageNo, int pageSize);
	
}
