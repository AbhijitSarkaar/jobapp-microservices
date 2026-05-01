package com.microservices.company.messaging;

import com.microservices.company.payload.ReviewMessageDTO;
import com.microservices.company.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessageDTO reviewMessageDTO) {
        companyService.updateCompanyRating(reviewMessageDTO);
    }
}
