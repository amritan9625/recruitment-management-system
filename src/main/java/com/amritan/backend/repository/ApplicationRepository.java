package com.amritan.backend.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Application;
import com.amritan.backend.enums.ApplicationStatus;

@Repository
public interface ApplicationRepository extends JpaRepository<Application	, Long>{

	Page<Application> findByJobId(Long jobId, Pageable pageable);

	Page<Application> findByCandidateId(Long candidateId, Pageable pageable);
	
	List<Application> findTop5ByOrderByIdDesc();

	Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);

	Page<Application> findByCandidateNameContainingIgnoreCase(String keyword, Pageable pageable);

	Page<Application> findByJobTitleContainingIgnoreCase(String keyword, Pageable pageable);
	
}
