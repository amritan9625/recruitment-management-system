package com.amritan.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{
	Page<Offer> findAll(Pageable pageable);
	
	
	Page<Offer> findBySalary(Double salary, Pageable pageable);
	Page<Offer> findBySalaryAndCandidateEmailIgnoreCase(Double salary, String email, Pageable pageable);
	
	Page<Offer> findByCandidateFirstNameContainingIgnoreCaseOrCandidateLastNameContainingIgnoreCase(
			String firstname, String lastName, Pageable pageable);
	Page<Offer> findByCandidateEmailIgnoreCase(String email, Pageable pageable);

	Page<Offer> findByJobTitleContainingIgnoreCase(String keyword, Pageable pageable);
	Page<Offer> findByJobTitleContainingIgnoreCaseAndCandidateEmailIgnoreCase(
			String keyword, String email, Pageable pageable);
	
}
