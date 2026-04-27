package com.microservice.review.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 2, message = "Title needs to be at least 2 characters")
    private String title;

    @NotNull
    @Size(min = 5, message = "Description needs to be at least 5 characters")
    private String description;

    @NotNull
    @Min(value = 1, message = "review needs to be more than or equal to 1")
    @Max(value = 5, message = "review needs to be less than or equal to 5")
    private Integer rating;

}
