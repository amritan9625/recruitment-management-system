package com.amritan.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amritan.backend.entity.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{

}