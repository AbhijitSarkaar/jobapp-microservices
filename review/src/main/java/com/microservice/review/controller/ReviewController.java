
package com.microservice.review.controller;

import com.microservice.review.exception.APIResponse;
import com.microservice.review.model.Review;
import com.microservice.review.payload.ReviewDTO;
import com.microservice.review.payload.ReviewRequestDTO;
import com.microservice.review.payload.ReviewWithCompanyDTO;
import com.microservice.review.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewWithCompanyDTO>> reviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return new ResponseEntity<>(reviewService.createReview(reviewRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO,
            @PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.updateReview(reviewRequestDTO, reviewId), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<APIResponse> deleteReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.deleteReview(reviewId), HttpStatus.OK);
    }

    @GetMapping("/reviews/average-rating")
    public Double getAverageRating(@RequestParam Long companyId) {
        List<ReviewWithCompanyDTO> reviews = reviewService.getAllReviews();
        return reviews.stream()
                .mapToDouble(item -> item.getReview().getRating())
                .average()
                .orElse(0.0);
    }
}
