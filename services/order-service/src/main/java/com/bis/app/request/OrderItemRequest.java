package com.bis.app.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/16/26
 * </blockquote></pre>
 */


public record OrderItemRequest(
        @NotNull UUID productId,
        @Min(1) Integer quantity
) {}
