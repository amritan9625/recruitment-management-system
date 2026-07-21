package com.amritan.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application	, Long>{

	List<Application> findByJobId(Long jobId);

	List<Application> findByCandidateId(Long candidateId);
}
