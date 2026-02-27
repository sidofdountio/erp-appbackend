package com.sidof.app.shops;

import com.sidof.app.response.ShopResponse;
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
 * Since    : 2/20/26
 * </blockquote></pre>
 */

@FeignClient(
        name = "shop-service",
        url = "${application.config.shop-url}"
)
public interface ShopClient {

    @GetMapping("/{shopId}")
    Optional<ShopResponse> findShopById(@PathVariable UUID shopId);
    @GetMapping("/{email}")
    Optional<ShopResponse> findShopByEmail(@PathVariable String email);
}
