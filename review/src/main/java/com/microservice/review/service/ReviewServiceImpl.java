package com.microservice.review.service;

import com.microservice.review.exception.APIResponse;
import com.microservice.review.exception.CustomResourceNotFoundException;
import com.microservice.review.model.Review;
import com.microservice.review.payload.ReviewDTO;
import com.microservice.review.payload.ReviewRequestDTO;
import com.microservice.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        reviews.forEach(item -> {
            System.out.println();
        });
        return reviews
                .stream()
                .map(item -> modelMapper.map(item, ReviewDTO.class))
                .toList();
    }

    @Override
    public ReviewDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        Review review = modelMapper.map(reviewRequestDTO, Review.class);
        return modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Review", "review id", reviewId.toString()));
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(ReviewRequestDTO reviewRequestDTO, Long reviewId) {
        Review review = modelMapper.map(getReviewById(reviewId), Review.class);

        review.setId(reviewId);
        if(reviewRequestDTO.getTitle() != null) review.setTitle(reviewRequestDTO.getTitle());
        if(reviewRequestDTO.getDescription() != null) review.setDescription(reviewRequestDTO.getDescription());
        if(reviewRequestDTO.getRating() != null) review.setRating(reviewRequestDTO.getRating());

        return modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    @Override
    public APIResponse deleteReview(Long reviewId) {
        getReviewById(reviewId);
        reviewRepository.deleteById(reviewId);
        return new APIResponse("Review with id " + reviewId + " deleted");
    }
}
