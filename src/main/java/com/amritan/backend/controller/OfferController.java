package com.amritan.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amritan.backend.dto.OfferDto;
import com.amritan.backend.service.OfferService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/offers")
@RequiredArgsConstructor
public class OfferController {

	private final OfferService offerService;
	
	
	@PostMapping
	public ResponseEntity<OfferDto> createOffer(@Valid @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.createOffer(dto);
		
		return new ResponseEntity<>(
				offerDto, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<OfferDto>> getAllOffers(){
		List<OfferDto> offerDtos = offerService.getAllOffers();
		
		return ResponseEntity.ok(offerDtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OfferDto> getOfferById(@PathVariable Long id){
		OfferDto offerDto = offerService.getOfferById(id);
		
		return ResponseEntity.ok(offerDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OfferDto> updateOffer(@PathVariable Long id, @RequestBody OfferDto dto){
		OfferDto offerDto = offerService.updateOffer(id, dto);
		
		return ResponseEntity.ok(offerDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteOffer(@PathVariable Long id){
		offerService.deleteOffer(id);
		
		return ResponseEntity.ok("Offer deleted successfully");
	}
}
