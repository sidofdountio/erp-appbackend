package com.bis.app.request;

import com.bis.app.model.PaymentMethod;
import com.bis.app.response.CustomerResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
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


public record PaymentRequest(

        @Positive
        BigDecimal amount,
        @NotNull
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        @NotNull UUID OrderId,
        String orderReference
) {}
