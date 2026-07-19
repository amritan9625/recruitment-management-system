package com.amritan.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amritan.backend.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application	, Long>{

}
