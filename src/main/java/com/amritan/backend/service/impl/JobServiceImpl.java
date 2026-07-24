package com.amritan.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amritan.backend.dto.JobDto;
import com.amritan.backend.entity.Job;
import com.amritan.backend.exception.ResourceNotFoundException;
import com.amritan.backend.mapper.JobMapper;
import com.amritan.backend.repository.JobRepository;
import com.amritan.backend.service.JobService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService{
	
	private final JobRepository jobRepository;

	@Override
	public JobDto createJob(JobDto jobDto) {
		Job job = JobMapper.mapToEntity(jobDto);
		Job savedJob = jobRepository.save(job);
		
		return JobMapper.mapToDto(savedJob);
	}

	@Override
	public List<JobDto> getAllJobs() {
		
		return jobRepository.findAll()
				.stream()
				.map(JobMapper::mapToDto)
				.toList();
	}

	@Override
	public JobDto getJobById(Long id) {
		Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
		
		return JobMapper.mapToDto(job);
	}

	@Override
	public JobDto updateJob(Long id, JobDto dto) {
		Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
		
		job.setTitle(dto.getTitle());
		job.setDescription(dto.getDescription());
		job.setLocation(dto.getLocation());
		job.setSalary(dto.getSalary());
		job.setJobType(dto.getJobType());
		job.setStatus(dto.getStatus());
		
		Job updateJob = jobRepository.save(job);
		
		return JobMapper.mapToDto(updateJob);
	}

	@Override
	public void deleteJob(Long id) {
		Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found with id: " + id));
		
		jobRepository.delete(job);
	}
	
	

}
