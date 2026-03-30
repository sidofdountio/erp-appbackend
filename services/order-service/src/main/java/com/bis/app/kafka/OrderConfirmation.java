package com.bis.app.kafka;

import com.bis.app.model.OrderItem;
import com.bis.app.model.PaymentMethod;
import com.bis.app.response.CustomerResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/1/26
 * </blockquote></pre>
 */

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
         List<OrderItem>orderItems
) {
}
