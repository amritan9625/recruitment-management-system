package com.amritan.backend.mapper;

import com.amritan.backend.dto.JobDto;
import com.amritan.backend.entity.Job;

public class JobMapper {

	public static Job mapToEntity(JobDto dto) {
		Job job = new Job();
		
		job.setId(dto.getId());
		job.setTitle(dto.getTitle());
		job.setDescription(dto.getDescription());
		job.setLocation(dto.getLocation());
		job.setSalary(dto.getSalary());
		job.setJobType(dto.getJobType());
		job.setStatus(dto.getStatus());
		
		return job;
		
	}
	
	public static JobDto mapToDto(Job job) {
		JobDto dto = new JobDto();
		
		dto.setId(job.getId());
		dto.setTitle(job.getTitle());
		dto.setDescription(job.getDescription());
		dto.setLocation(dto.getLocation());
		dto.setSalary(job.getSalary());
		dto.setJobType(job.getJobType());
		dto.setStatus(job.getStatus());
		
		return dto;
	}
}
