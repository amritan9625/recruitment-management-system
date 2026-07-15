package com.amritan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private String location;
	
	private Double salary;
	
	private String jobType;
	
	private String status;
}
