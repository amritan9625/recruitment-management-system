package com.amritan.backend.entity;


import com.amritan.backend.enums.JobStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String location;
	
	private Double salary;
	
	private String jobType;
	
	@Enumerated(EnumType.STRING)
	private JobStatus status;
}
