package com.microservices.job.controller;

import com.microservices.job.exception.APIResponse;
import com.microservices.job.external.Company;
import com.microservices.job.payload.JobDTO;
import com.microservices.job.payload.JobRequestDTO;
import com.microservices.job.service.JobService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    JobService jobService;

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> getJobs() {
        return new ResponseEntity<>(jobService.getJobs(), HttpStatus.OK);
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId) {
        return new ResponseEntity<>(jobService.getJobById(jobId), HttpStatus.OK);
    }

    @PostMapping("/jobs")
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobRequestDTO jobRequestDTO) {
        return new ResponseEntity<>(jobService.createJob(jobRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<JobDTO> createJob(
            @Valid @RequestBody JobRequestDTO jobRequestDTO,
            @PathVariable Long jobId
    ) {
        return new ResponseEntity<>(jobService.updateJob(jobRequestDTO, jobId), HttpStatus.CREATED);
    }

    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<APIResponse> deleteJob(@PathVariable Long jobId) {
        return new ResponseEntity<>(jobService.deleteJob(jobId), HttpStatus.OK);
    }
}
