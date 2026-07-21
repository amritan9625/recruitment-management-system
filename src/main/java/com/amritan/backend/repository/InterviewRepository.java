package com.amritan.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amritan.backend.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long>{

}
