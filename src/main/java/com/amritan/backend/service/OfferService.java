package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.OfferDto;

public interface OfferService {

	OfferDto createOffer(OfferDto offerDto);

	OfferDto getOfferById(Long id);

	List<OfferDto> getAllOffers();

	OfferDto updateOffer(Long id, OfferDto offerDto);

	void deleteOffer(Long id);
}
