package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.JobDto;

public interface JobService {

	JobDto createJob(JobDto jobDto);

    List<JobDto> getAllJobs();

    JobDto getJobById(Long id);

    JobDto updateJob(Long id, JobDto jobDto);

    void deleteJob(Long id);
}
