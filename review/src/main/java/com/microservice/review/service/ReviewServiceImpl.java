
package com.microservice.review.service;

import com.microservice.review.client.CompanyClient;
import com.microservice.review.exception.APIResponse;
import com.microservice.review.exception.CustomResourceNotFoundException;
import com.microservice.review.external.Company;
import com.microservice.review.messaging.ReviewMessageProducer;
import com.microservice.review.model.Review;
import com.microservice.review.payload.ReviewDTO;
import com.microservice.review.payload.ReviewRequestDTO;
import com.microservice.review.payload.ReviewWithCompanyDTO;
import com.microservice.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CompanyClient companyClient;

    @Autowired
    ReviewMessageProducer reviewMessageProducer;

    @Override
    public List<ReviewWithCompanyDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        List<ReviewDTO> reviewDtos = reviews
                .stream()
                .map(item -> modelMapper.map(item, ReviewDTO.class))
                .toList();

        List<ReviewWithCompanyDTO> reviewWithCompanyDtoList = new ArrayList<>();

        for (ReviewDTO reviewDto : reviewDtos) {
            ReviewWithCompanyDTO reviewWithCompanyDTO = new ReviewWithCompanyDTO();
            reviewWithCompanyDTO.setReview(reviewDto);
            reviewWithCompanyDTO.setCompany(getCompanyById(reviewDto.getCompanyId()));

            reviewWithCompanyDtoList.add(reviewWithCompanyDTO);
        }

        return reviewWithCompanyDtoList;
    }

    @Override
    public ReviewDTO createReview(ReviewRequestDTO reviewRequestDTO) {

        getCompanyById(reviewRequestDTO.getCompanyId());

        Review review = modelMapper.map(reviewRequestDTO, Review.class);

        review = reviewRepository.save(review);

        reviewMessageProducer.sendMessage(review);

        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Review", "review id", reviewId.toString()));

        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(ReviewRequestDTO reviewRequestDTO, Long reviewId) {

        getCompanyById(reviewRequestDTO.getCompanyId());

        Review review = modelMapper.map(getReviewById(reviewId), Review.class);

        review.setId(reviewId);
        review.setCompanyId(reviewRequestDTO.getCompanyId());
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

    public Company getCompanyById(Long companyId) {
        return companyClient.getCompany(companyId);
    }
}
