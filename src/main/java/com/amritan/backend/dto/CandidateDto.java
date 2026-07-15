package com.amritan.backend.dto;

import lombok.Data;

@Data
public class CandidateDto {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	private String skills;
	
	private Integer experience;
	
	private String resumeUrl;
}
