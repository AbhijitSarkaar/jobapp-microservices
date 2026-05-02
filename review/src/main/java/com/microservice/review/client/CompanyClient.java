
package com.microservice.review.client;

import com.microservice.review.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "COMPANY-SERVICE",
        url = "${company-service.url}"
)
public interface CompanyClient {
    @GetMapping("/api/companies/{companyId}")
    Company getCompany(@PathVariable("companyId") Long companyId);
}
