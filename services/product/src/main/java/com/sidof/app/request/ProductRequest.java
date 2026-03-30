package com.sidof.app.request;

import jakarta.persistence.Column;

import java.math.BigDecimal;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/14/26
 * </blockquote></pre>
 */

public record ProductRequest(
         String name,
         BigDecimal price,
         double availableQuantity,
         String sku,
         String category_id,
         String shop_id

) {
}
