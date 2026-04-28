package com.microservices.job.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JobRequestDTO {
    @NotNull
    @Size(min = 3, message = "title needs to be at least 3 characters")
    private String title;

    @NotNull
    @Size(min = 5, message = "description needs to be at least 5 characters")
    private String description;

    @NotNull
    private Integer salary;

    @NotNull
    private String location;

    @NotNull
    private Long companyId;
}
