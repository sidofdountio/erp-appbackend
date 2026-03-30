package com.bis.app.order;

import com.bis.app.customer.Customer;
import com.bis.app.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/4/26
 * </blockquote></pre>
 */

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer  ,
        List<Product> products
) {
}
