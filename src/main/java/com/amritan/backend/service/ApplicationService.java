package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.ApplicationDto;

public interface ApplicationService {

	ApplicationDto createApplication(ApplicationDto dto);

	List<ApplicationDto> getAllApplications();

	ApplicationDto getApplicationById(Long id);

    ApplicationDto updateApplication(Long id,
                                     ApplicationDto dto);

    void deleteApplication(Long id);
}
