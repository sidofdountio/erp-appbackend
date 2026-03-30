package com.bis.app.product;

import com.bis.app.response.ProductResponse;
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
@FeignClient(name = "product-service", url = "${application.config.product-url}")
public interface ProductClient {

    @GetMapping("/api/v1/bis/catalogue/product-1/{productId}")
    Optional<ProductResponse> getProductById(@PathVariable("productId") UUID productId);

}
