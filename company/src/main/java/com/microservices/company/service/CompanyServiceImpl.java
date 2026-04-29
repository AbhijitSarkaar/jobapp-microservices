package com.microservices.company.service;

import com.microservices.company.exception.APIResponse;
import com.microservices.company.exception.CustomResourceNotFoundException;
import com.microservices.company.model.Company;
import com.microservices.company.payload.CompanyDTO;
import com.microservices.company.payload.CompanyRequestDTO;
import com.microservices.company.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, CompanyDTO.class))
                .toList();
    }

    @Override
    public CompanyDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> {
                    log.info(":::CustomResourceNotFoundException:::");
                    return new CustomResourceNotFoundException("Company", "company id", companyId.toString());
                });
        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO createCompany(CompanyRequestDTO companyRequestDTO) {
        Company company = modelMapper.map(companyRequestDTO, Company.class);
        return modelMapper.map(companyRepository.save(company), CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(CompanyRequestDTO companyRequestDTO, Long companyId) {

        Company company = modelMapper.map(getCompanyById(companyId), Company.class);
        company.setId(companyId);
        if(companyRequestDTO.getName() != null) company.setName(companyRequestDTO.getName());
        if(companyRequestDTO.getDescription() != null) company.setDescription(companyRequestDTO.getDescription());

        return modelMapper.map(companyRepository.save(company), CompanyDTO.class);
    }

    @Override
    public APIResponse deleteCompany(Long companyId) {
        getCompanyById(companyId);
        companyRepository.deleteById(companyId);
        return new APIResponse("Company with id " + companyId + " deleted");
    }
}
