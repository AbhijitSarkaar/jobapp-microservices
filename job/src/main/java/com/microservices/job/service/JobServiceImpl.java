package com.microservices.job.service;

import com.microservices.job.clients.CompanyClient;
import com.microservices.job.exception.APIResponse;
import com.microservices.job.exception.CustomResourceNotFoundException;
import com.microservices.job.external.Company;
import com.microservices.job.mapper.JobMapper;
import com.microservices.job.model.Job;
import com.microservices.job.payload.JobDTO;
import com.microservices.job.payload.JobRequestDTO;
import com.microservices.job.payload.JobWithCompanyDTO;
import com.microservices.job.repository.JobRepository;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CompanyClient companyClient;

    @Override
    public List<JobWithCompanyDTO> getJobs() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDtos = new ArrayList<>();
        jobs.forEach(job -> {
            Company company = getCompanyById(job.getCompanyId());
            jobWithCompanyDtos.add(
                    JobMapper.JobWithCompanyDTOMapper(job, company)
            );
        });

        return jobWithCompanyDtos;
    }

    @Override
    public JobDTO createJob(JobRequestDTO jobRequestDTO) {

        getCompanyById(jobRequestDTO.getCompanyId());

        Job payload = modelMapper.map(jobRequestDTO, Job.class);

        return modelMapper.map(jobRepository.save(payload), JobDTO.class);
    }

    @Override
    public JobDTO updateJob(JobRequestDTO jobRequestDTO, Long jobId) {
        getCompanyById(jobRequestDTO.getCompanyId());

        Job job = modelMapper.map(getJobById(jobId), Job.class);
        job.setId(jobId);
        job.setCompanyId(jobRequestDTO.getCompanyId());

        if(jobRequestDTO.getTitle() != null) job.setTitle(jobRequestDTO.getTitle());
        if(jobRequestDTO.getDescription() != null) job.setDescription(jobRequestDTO.getDescription());
        if(jobRequestDTO.getSalary() != null) job.setSalary(jobRequestDTO.getSalary());
        if(jobRequestDTO.getLocation() != null) job.setLocation(jobRequestDTO.getLocation());

        return modelMapper.map(jobRepository.save(job), JobDTO.class);
    }

    @Override
    public APIResponse deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
        return new APIResponse("Job with id " + jobId + " deleted");
    }

    @Override
    public JobWithCompanyDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Job", "job id", jobId)
                );
        Company company = getCompanyById(job.getCompanyId());
        return JobMapper.JobWithCompanyDTOMapper(job, company);
    }

    public Company getCompanyById(Long companyId) {
        return companyClient.getCompany(companyId);
    }

}
