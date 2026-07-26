package com.amritan.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findTop5ByOrderByIdDesc();
	
	Page<Job> findAll(Pageable pageable);
	
	Page<Job> findByTitleContainingIgnoreCase(
	        String keyword,
	        Pageable pageable
	);
	
	Page<Job> findByStatus(
	        String status,
	        Pageable pageable
	);
	
	Page<Job> findByLocationContainingIgnoreCase(
	        String location,
	        Pageable pageable
	);
}
