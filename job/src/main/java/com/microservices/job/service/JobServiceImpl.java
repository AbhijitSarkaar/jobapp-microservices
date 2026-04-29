package com.microservices.job.service;

import com.microservices.job.exception.APIResponse;
import com.microservices.job.exception.CustomResourceNotFoundException;
import com.microservices.job.external.Company;
import com.microservices.job.model.Job;
import com.microservices.job.payload.JobDTO;
import com.microservices.job.payload.JobRequestDTO;
import com.microservices.job.payload.JobWithCompanyDTO;
import com.microservices.job.repository.JobRepository;
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

    @Value("${company.service.url}")
    private String companyServiceUrl;

    @Override
    public List<JobWithCompanyDTO> getJobs() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDtos = jobs.stream().map(job -> modelMapper.map(job, JobDTO.class)).toList();

        List<JobWithCompanyDTO> jobWithCompanyDtos = new ArrayList<>();

        jobDtos.forEach(jobDTO -> {

            RestTemplate restTemplate = new RestTemplate();
            Company company = restTemplate.getForObject(companyServiceUrl + "/" + jobDTO.getCompanyId(), Company.class);

            JobWithCompanyDTO jobWithCompanyDto = new JobWithCompanyDTO();
            jobWithCompanyDto.setJob(jobDTO);
            jobWithCompanyDto.setCompany(company);

            jobWithCompanyDtos.add(jobWithCompanyDto);

        });

        return jobWithCompanyDtos;
    }

    @Override
    public JobDTO createJob(JobRequestDTO jobRequestDTO) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(companyServiceUrl + "/" + jobRequestDTO.getCompanyId(), Company.class);
        Job payload = modelMapper.map(jobRequestDTO, Job.class);
        return modelMapper.map(jobRepository.save(payload), JobDTO.class);
    }

    @Override
    public JobDTO updateJob(JobRequestDTO jobRequestDTO, Long jobId) {

        Job job = modelMapper.map(getJobById(jobId), Job.class);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(companyServiceUrl + "/" + jobRequestDTO.getCompanyId(), Company.class);

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
    public JobDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Job", "job id", jobId)
                );
        return modelMapper.map(job, JobDTO.class);
    }

}
