package com.microservice.review.payload;

import lombok.Data;

@Data
public class ReviewMessageDTO {
    private Long id;
    private String title;
    private String description;
    private Double rating;
    private Long companyId;
}
