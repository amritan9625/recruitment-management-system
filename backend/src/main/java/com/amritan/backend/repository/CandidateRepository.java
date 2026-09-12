package com.amritan.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Candidate;
import com.amritan.backend.entity.User;
import com.amritan.backend.enums.CandidateStatus;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>{

	List<Candidate> findTop5ByOrderByIdDesc();
	
	Page<Candidate> findAll(Pageable pageable);
	
	Page<Candidate> findByFirstNameContainingIgnoreCase(
	        String keyword,
	        Pageable pageable
	);
	
	Page<Candidate> findByStatus(
	        CandidateStatus status,
	        Pageable pageable
	);
	
    Candidate findByEmailIgnoreCase(String email);
    
    Optional<Candidate> findByUser(User user);
}
