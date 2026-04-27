package com.microservices.job.service;

import com.microservices.job.exception.APIResponse;
import com.microservices.job.payload.JobDTO;
import com.microservices.job.payload.JobRequestDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface JobService {
    List<JobDTO> getJobs();

    JobDTO createJob(@Valid JobRequestDTO jobRequestDTO);

    JobDTO updateJob(@Valid JobRequestDTO jobRequestDTO, Long jobId);

    APIResponse deleteJob(Long jobId);
}
