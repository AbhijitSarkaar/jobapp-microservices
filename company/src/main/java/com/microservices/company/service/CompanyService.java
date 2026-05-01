package com.microservices.company.service;

import com.microservices.company.exception.APIResponse;
import com.microservices.company.payload.CompanyDTO;
import com.microservices.company.payload.CompanyRequestDTO;
import com.microservices.company.payload.ReviewMessageDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();

    CompanyDTO getCompanyById(Long companyId);

    CompanyDTO createCompany(CompanyRequestDTO companyRequestDTO);

    CompanyDTO updateCompany(@Valid CompanyRequestDTO companyRequestDTO, Long companyId);

    APIResponse deleteCompany(Long companyId);

    public void updateCompanyRating(ReviewMessageDTO reviewMessageDTO);
}

