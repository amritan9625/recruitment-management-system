package com.amritan.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Interview;
import com.amritan.backend.enums.InterviewStatus;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{
	Page<Interview> findAll(Pageable pageable);
	
	Page<Interview> findByStatus(InterviewStatus status, Pageable pageable);
	
	
	Page<Interview> findByInterviewerContainingIgnoreCase(String keyword, Pageable pageable);
}