package com.microservices.job.payload;

import lombok.Data;

@Data
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private Integer salary;
    private String location;
    private Long companyId;
}
