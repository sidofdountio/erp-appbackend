package com.bis.app.model;

import jakarta.persistence.*;
import lombok.*;

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
 * Since    : 1/24/26
 * </blockquote></pre>
 */


@Entity
@Table(
        name = "catalog_products",
        indexes = {
                @Index(name = "idx_catalog_product_id", columnList = "productId"),
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String sku; // Stock Keeping Unit


    @Column(nullable = false)
    private String name;
    @Column(length = 2000)
    private String description;

    @Column(nullable = false, unique = true)
    private String productId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;
    @Column(precision = 12, scale = 2)
    private BigDecimal promotionPrice;

    private double availableQuantity;
    @Column(nullable = false)
    private boolean inStock;
    private String unitOfMeasure;

    private boolean active = true;
    private boolean promotion=false;

    private LocalDateTime promotionStart;
    private LocalDateTime promotionEnd;


    @Column(nullable = false, unique = true)
    private UUID shopId;
    private String shopName;

    @Column(nullable = false)
    private String categoryId;
    private String categoryName;

    private String imageUrl;

    private Long soldCount;
    private Double popularityScore;

    //  store coordinate
    private Double latitude;
    private Double longitude;


}

