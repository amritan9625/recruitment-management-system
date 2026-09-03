package com.amritan.backend.service;


import com.amritan.backend.dto.CandidateDto;
import com.amritan.backend.dto.PageResponse;
import com.amritan.backend.enums.CandidateStatus;

public interface CandidateService {

	CandidateDto createCandidate(CandidateDto candidateDTO);
	
	PageResponse<CandidateDto> getAllCandidates(
	        int pageNo,
	        int pageSize,
	        String sortBy,
	        String sortDir
	);

    CandidateDto getCandidateById(Long id);

    CandidateDto updateCandidate(Long id, CandidateDto candidateDTO);

    void deleteCandidate(Long id);
    
    PageResponse<CandidateDto> searchCandidates(
            String keyword,
            int pageNo,
            int pageSize
    );
    
    PageResponse<CandidateDto> getCandidatesByStatus(
            CandidateStatus status,
            int pageNo,
            int pageSize
    );

}
