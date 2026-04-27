package com.microservices.job.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @NotNull
    @Size(min = 3, message = "title needs to be at least 3 characters")
    private String title;

    @NotNull
    @Size(min = 5, message = "title needs to be at least 5 characters")
    private String description;

    @NotNull
    private Integer salary;

    @NotNull
    private String location;
}
