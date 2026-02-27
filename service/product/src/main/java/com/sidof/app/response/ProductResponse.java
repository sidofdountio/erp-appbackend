package com.sidof.app.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/14/26
 * </blockquote></pre>
 */
@Builder
@Getter @Setter
public class ProductResponse {
    private UUID id;
    private String name;

    private BigDecimal price;
    private double availableQuantity;

    private String sku;
    private boolean active ;


    private UUID shopId;
    private String categoryName;

    private String createdBy;

}
