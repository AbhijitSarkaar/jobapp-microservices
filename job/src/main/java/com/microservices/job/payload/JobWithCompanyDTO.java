package com.microservices.job.payload;

import com.microservices.job.external.Company;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class JobWithCompanyDTO {
    private Long id;
    private String title;
    private String description;
    private Integer salary;
    private String location;

    private Company company;
}
