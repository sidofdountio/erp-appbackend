package com.bis.app.response;

import com.bis.app.model.OrderItem;
import com.bis.app.model.OrderStatus;
import com.bis.app.model.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/17/26
 * </blockquote></pre>
 */

@Builder
@Getter
@Setter
public class OrderResponse {

    private UUID id;
    private BigDecimal totalAmount;
    private String reference;
    private OrderStatus status;

    private String paymentMethod;


    private UUID customerId;
    private UUID shopId;
    private List<OrderItem> items;

    private LocalDateTime createdAt;
}
