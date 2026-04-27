package com.microservice.review.service;

import com.microservice.review.exception.APIResponse;
import com.microservice.review.payload.ReviewDTO;
import com.microservice.review.payload.ReviewRequestDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();

    ReviewDTO createReview(ReviewRequestDTO reviewRequestDTO);

    ReviewDTO getReviewById(Long reviewId);

    ReviewDTO updateReview(ReviewRequestDTO reviewRequestDTO, Long reviewId);

    APIResponse deleteReview(Long reviewId);
}
