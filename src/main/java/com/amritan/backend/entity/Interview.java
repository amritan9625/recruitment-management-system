package com.amritan.backend.entity;

import java.time.LocalDateTime;

import com.amritan.backend.enums.InterviewStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime interviewDate;

    private String mode;

    private String interviewer;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;
    
    @ManyToOne
    @JoinColumn(name="application_id")
    private Application application;
}