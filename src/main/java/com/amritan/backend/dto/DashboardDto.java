package com.amritan.backend.dto;

import lombok.Data;

@Data
public class DashboardDto {

	private long totalUsers;
    private long totalCandidates;
    private long totalJobs;
    private long totalApplications;
    private long totalInterviews;
    private long totalOffers;
}
