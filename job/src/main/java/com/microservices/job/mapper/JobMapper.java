package com.microservices.job.mapper;

import com.microservices.job.external.Company;
import com.microservices.job.model.Job;
import com.microservices.job.payload.JobWithCompanyDTO;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobMapper {

    public static JobWithCompanyDTO JobWithCompanyDTOMapper(Job job, Company company) {
        JobWithCompanyDTO jobWithCompanyDto = new JobWithCompanyDTO();

        jobWithCompanyDto.setTitle(job.getTitle());
        jobWithCompanyDto.setDescription(job.getDescription());
        jobWithCompanyDto.setSalary(job.getSalary());
        jobWithCompanyDto.setLocation(job.getLocation());
        jobWithCompanyDto.setId(job.getId());

        jobWithCompanyDto.setCompany(company);

        return jobWithCompanyDto;
    }
}
