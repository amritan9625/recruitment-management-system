package com.amritan.backend.entity;

import java.time.LocalDate;

import com.amritan.backend.enums.OfferStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "offers")
@Data
public class Offer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double salary;

    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
