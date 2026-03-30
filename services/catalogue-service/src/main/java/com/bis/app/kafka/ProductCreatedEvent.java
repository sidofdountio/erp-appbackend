package com.bis.app.kafka;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/1/26
 * </blockquote></pre>
 */


@Data
@Builder
public class ProductCreatedEvent {
    private UUID productId;
    private String sku;

    private String name;
    private String description;

    private BigDecimal price;
    private BigDecimal promotionPrice;

    private Double availableQuantity;
    private boolean inStock;
    private String unitOfMeasure;

    private boolean active;
    private boolean promotion;

    private UUID shopId;
    private String shopName;

    private UUID categoryId;
    private String categoryName;

    private String imageUrl;

    private LocalDateTime createdAt;

    //  store coordinate
    private String latitude;
    private String longitude;
}
