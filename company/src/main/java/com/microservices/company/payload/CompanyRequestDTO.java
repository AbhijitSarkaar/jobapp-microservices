package com.microservices.company.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequestDTO {

    @NotNull
    @Size(min = 3, message = "Company name must be at least 3 characters")
    private String name;

    @NotNull
    @Size(min = 5, message = "Company description must be at least 5 characters")
    private String description;

}
