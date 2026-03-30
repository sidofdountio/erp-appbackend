package com.bis.app.payment;

import java.math.BigDecimal;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/4/26
 * </blockquote></pre>
 */


public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String CustomerFirstName,
        String CustomerLastName,
        String CustomerEmail
) {
}
