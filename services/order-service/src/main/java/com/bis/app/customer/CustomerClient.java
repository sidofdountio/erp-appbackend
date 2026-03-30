package com.bis.app.customer;

import com.bis.app.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/17/26
 * </blockquote></pre>
 */
@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/api/v1/bis/customer/{customerId}")
    Optional<CustomerResponse>getCustomer(@PathVariable UUID customerId);

}
