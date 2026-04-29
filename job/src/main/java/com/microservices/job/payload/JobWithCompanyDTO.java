package com.microservices.job.payload;

import com.microservices.job.external.Company;
import lombok.Data;

@Data
public class JobWithCompanyDTO {
    private JobDTO job;
    private Company company;
}
