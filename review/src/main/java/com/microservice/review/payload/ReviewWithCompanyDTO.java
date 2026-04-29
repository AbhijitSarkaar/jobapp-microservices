package com.microservice.review.payload;

import com.microservice.review.external.Company;
import lombok.Data;

@Data
public class ReviewWithCompanyDTO {
    private ReviewDTO review;
    private Company company;
}
