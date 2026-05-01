
package com.microservice.review.messaging;

import com.microservice.review.model.Review;
import com.microservice.review.payload.ReviewMessageDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Review review) {

        ReviewMessageDTO reviewMessageDTO = new ReviewMessageDTO();

        reviewMessageDTO.setId(review.getId());
        reviewMessageDTO.setDescription(review.getDescription());
        reviewMessageDTO.setTitle(review.getTitle());
        reviewMessageDTO.setRating(review.getRating());
        reviewMessageDTO.setCompanyId(review.getCompanyId());

        rabbitTemplate.convertAndSend("companyRatingQueue", reviewMessageDTO);

    }
}
