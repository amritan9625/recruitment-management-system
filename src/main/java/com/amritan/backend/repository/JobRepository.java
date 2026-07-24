package com.amritan.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

	List<Job> findTop5ByOrderByIdDesc();
}
