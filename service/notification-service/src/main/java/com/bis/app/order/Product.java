package com.bis.app.order;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/4/26
 * </blockquote></pre>
 */

public record Product(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
