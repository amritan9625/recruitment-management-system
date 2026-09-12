package com.amritan.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateProfileRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50)
    private String firstName;

    private String lastName;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Skills are required")
    @Size(min = 7, max = 100)
    private String skills;

    @NotNull(message = "Experience is required")
    @PositiveOrZero(message = "Experience cannot be negative")
    private Integer experience;

    @NotBlank(message = "Resume URL is required")
    private String resumeUrl;
}