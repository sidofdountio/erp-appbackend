package com.bis.app.gatewayser.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 3/5/26
 * </blockquote></pre>
 */

@Configuration
public class CustomerRouteLocator {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // 1. SHOP SERVICE (Secured with Token Relay)
                .route("shop-service", r -> r.path("/api/v1/bis/shops/**")
                        .filters(f -> f.tokenRelay()
                                .preserveHostHeader())
                        .uri("lb://SHOP-SERVICE"))

                // 2. AUTH SERVICE (Usually public or login endpoints)
                .route("auth-service", r -> r.path("/api/v1/bis/users/**")
                        .filters(f -> f.tokenRelay().preserveHostHeader())
                        .uri("lb://AUTH-SERVICE"))

                // 3. CUSTOMER SERVICE
                .route("customer-service", r -> r.path("/api/v1/bis/customers/**")
                        .filters(f -> f.tokenRelay().preserveHostHeader())
                        .uri("lb://CUSTOMER-SERVICE"))

                // 4. ORDER SERVICE
                .route("order-service", r -> r.path("/api/v1/bis/orders/**")
                        .filters(f -> f.tokenRelay().preserveHostHeader())
                        .uri("lb://ORDER-SERVICE"))

                // 5. PRODUCT SERVICE
                .route("product-service", r -> r.path("/api/v1/bis/products/**")
                        .uri("lb://PRODUCT-SERVICE"))

                // 6. CATALOGUE SERVICE
                .route("catalogue-service", r -> r.path("/api/v1/bis/catalogues/**")
                        .filters(f -> f.tokenRelay().preserveHostHeader())
                        .uri("lb://CATALOGUE-SERVICE"))

                //  TEST ROUTE (No security needed for debugging)
                .route("shop-test", r -> r.path("/test/**")
                        .uri("lb://SHOP-SERVICE"))

                .build();
    }
}
