package com.bis.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/24/26
 * </blockquote></pre>
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogProductResponse {

    /* =========================================================
       PRODUCT INFO
       ========================================================= */

    private String productId;
    private String productName;
    private String description;
    private String imageUrl;
    private String categoryId;
    private String categoryName;

    /* =========================================================
       PRICING
       ========================================================= */

    private BigDecimal price;
    private BigDecimal promotionPrice;
    private boolean promotionActive;

    /* =========================================================
       INVENTORY
       ========================================================= */

    private boolean inStock;
    private double availableQuantity;

    /* =========================================================
       SHOP INFO
       ========================================================= */

    private UUID shopId;
    private String shopName;
    private boolean shopOpen;

    /* =========================================================
       LOCATION / GEO
       ========================================================= */

    private Double shopLatitude;
    private Double shopLongitude;
    private Double distanceKm;

    /* =========================================================
       ANALYTICS / RANKING
       ========================================================= */

    private Long popularityScore;
    private Long totalSales;
}
