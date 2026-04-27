package com.microservices.company.controller;

import com.microservices.company.exception.APIResponse;
import com.microservices.company.payload.CompanyDTO;
import com.microservices.company.payload.CompanyRequestDTO;
import com.microservices.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId) {
        return new ResponseEntity<>(companyService.getCompanyById(companyId), HttpStatus.OK);
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyDTO> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO) {
        return new ResponseEntity<>(companyService.createCompany(companyRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/companies/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(
            @Valid @RequestBody CompanyRequestDTO companyRequestDTO,
            @PathVariable Long companyId
    ) {
        return new ResponseEntity<>(companyService.updateCompany(companyRequestDTO, companyId), HttpStatus.OK);
    }

    @DeleteMapping("/companies/{companyId}")
    public ResponseEntity<APIResponse> deleteCompany(@PathVariable Long companyId) {
        return new ResponseEntity<>(companyService.deleteCompany(companyId), HttpStatus.OK);
    }
}
