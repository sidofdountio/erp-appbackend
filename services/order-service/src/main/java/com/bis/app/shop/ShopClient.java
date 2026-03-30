package com.bis.app.shop;

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
 * Since    : 1/27/26
 * </blockquote></pre>
 */

@FeignClient(
        name = "shop-service",
        url = "${application.config.shop-url}"
)
public interface ShopClient {
    @GetMapping("/api/v1/bis/public/shops/{shopId}")
    Optional<CustomerResponse> getShop(@PathVariable UUID shopId);
}
