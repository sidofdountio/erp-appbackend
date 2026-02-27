package com.bis.app.notification;

import com.bis.app.model.PaymentMethod;

import java.math.BigDecimal;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/1/26
 * </blockquote></pre>
 */

public record PaymentNotificationRequest(
       String orderReference,
       BigDecimal amount,
       PaymentMethod paymentMethod,
       String customerFirstName,
       String customerLastName,
        String customerEmail
) {
}
