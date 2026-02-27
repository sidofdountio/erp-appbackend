package com.bis.app.service;

import com.bis.app.model.Payment;
import com.bis.app.request.PaymentRequest;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/1/26
 * </blockquote></pre>
 */

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request){
        return Payment.builder()
                .amount(request.amount())
                .PaymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();

    }
}
