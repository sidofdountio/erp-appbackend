package com.bis.app.request;

import com.bis.app.customer.Customer;
import com.bis.app.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/1/26
 * </blockquote></pre>
 */

public record PaymentRequest(
        UUID id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        UUID orderId,
        String orderReference,
        Customer customer
) {
}
