package com.bis.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/16/26
 * </blockquote></pre>
 */

public record OrderLineRequest(
        @NotNull UUID productId,
        @NotNull UUID orderId,
        @Min(1) Integer quantity,
        @Positive
        BigDecimal priceAtPurchase
) {
}
