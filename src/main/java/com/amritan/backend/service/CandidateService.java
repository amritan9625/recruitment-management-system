package com.amritan.backend.service;

import java.util.List;

import com.amritan.backend.dto.CandidateDto;

public interface CandidateService {

	CandidateDto createCandidate(CandidateDto candidateDTO);

    CandidateDto getCandidateById(Long id);

    List<CandidateDto> getAllCandidates();

    CandidateDto updateCandidate(Long id, CandidateDto candidateDTO);

    void deleteCandidate(Long id);
}
