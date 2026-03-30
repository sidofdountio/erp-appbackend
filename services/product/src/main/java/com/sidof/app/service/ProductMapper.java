package com.sidof.app.service;

import com.sidof.app.kafka.ProductCreatedEvent;
import com.sidof.app.model.Category;
import com.sidof.app.model.Product;
import com.sidof.app.request.ProductRequest;
import com.sidof.app.response.ProductResponse;
import com.sidof.app.response.ShopResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/14/26
 * </blockquote></pre>
 */

@Service
@Slf4j
public class ProductMapper {
    public Product toProduct(ProductRequest request, Category category,UUID shopId) {
        return Product.builder()
                .name(request.name())
                .sku(request.sku())
                .category(category)
                .shopId(shopId)

                .build();
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .createdBy(product.getCreatedBy()) // From our Auditing system!
                .build();
    }


    public ProductCreatedEvent toProductCreatedEvent(Product product, ShopResponse shop) {
        log.info("Creating product event dto...");
        // Map entity to event
        return ProductCreatedEvent.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())

                .price(product.getPrice())
                .promotionPrice(product.getPromotionPrice())

                .availableQuantity(product.getAvailableQuantity())
                .inStock(product.isInStock())

                .sku(product.getSku())
                .active(product.isActive())
                .promotion(product.isPromotion())

                .unitOfMeasure(product.getUnitOfMeasure())

                .shopId(product.getShopId())
                .shopName(shop.getName())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())

                .imageUrl(product.getImageUrl())

                .createdAt(product.getCreatedAt())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .build();
    }
}
