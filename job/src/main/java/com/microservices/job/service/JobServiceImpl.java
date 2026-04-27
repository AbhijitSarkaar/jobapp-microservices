package com.microservices.job.service;

import com.microservices.job.exception.APIResponse;
import com.microservices.job.exception.CustomResourceNotFoundException;
import com.microservices.job.model.Job;
import com.microservices.job.payload.JobDTO;
import com.microservices.job.payload.JobRequestDTO;
import com.microservices.job.repository.JobRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<JobDTO> getJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(job -> modelMapper.map(job, JobDTO.class)).toList();
    }

    @Override
    public JobDTO createJob(JobRequestDTO jobRequestDTO) {
        Job payload = modelMapper.map(jobRequestDTO, Job.class);
        return modelMapper.map(jobRepository.save(payload), JobDTO.class);
    }

    @Override
    public JobDTO updateJob(JobRequestDTO jobRequestDTO, Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow( () -> new CustomResourceNotFoundException("Job", "job id", jobId)
                );
        if(jobRequestDTO.getTitle() != null) job.setTitle(jobRequestDTO.getTitle());
        if(jobRequestDTO.getDescription() != null) job.setDescription(jobRequestDTO.getDescription());
        if(jobRequestDTO.getSalary() != null) job.setSalary(jobRequestDTO.getSalary());
        if(jobRequestDTO.getLocation() != null) job.setLocation(jobRequestDTO.getLocation());
        job.setId(jobId);

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
