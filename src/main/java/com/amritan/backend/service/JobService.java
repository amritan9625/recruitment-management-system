package com.amritan.backend.service;



import com.amritan.backend.dto.JobDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.JobStatus;

public interface JobService {

	JobDto createJob(JobDto jobDto);

	PageResponse<JobDto> getAllJobs( int pageNo, int pageSize,
									String sortBy, String sortDir );

    JobDto getJobById(Long id);

    JobDto updateJob(Long id, JobDto jobDto);

    void deleteJob(Long id);
    

    PageResponse<JobDto> getJobsByStatus( JobStatus status, int pageNo, int pageSize );

    PageResponse<JobDto> getJobsByLocation( String location, int pageNo, int pageSize );
}
