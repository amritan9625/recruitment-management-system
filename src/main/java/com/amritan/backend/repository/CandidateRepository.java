package com.amritan.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>{

	List<Candidate> findTop5ByOrderByIdDesc();
}
