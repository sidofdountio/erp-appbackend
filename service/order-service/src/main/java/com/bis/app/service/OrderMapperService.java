package com.bis.app.service;

import com.bis.app.model.Order;
import com.bis.app.model.OrderStatus;
import com.bis.app.request.OrderRequest;
import com.bis.app.response.OrderResponse;
import com.bis.app.shop.ShopClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderMapperService {
    private final ShopClient shopClient;
    private final OrderItemMapper orderItemMapper;


    public Order createOrder(OrderRequest request) {

        var shop = shopClient.getShop(request.shopId()).get();
        //  Map request items to Entities and fetch prices
        var orderItems = orderItemMapper.toOrderItem(request);

        // 2. Calculate Grand Total
        BigDecimal total = orderItems.stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Build and Save Order
        return toOrder(total, shop.getId());
    }


    public OrderResponse mapToResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .customerId(order.getCustomerId())
                .shopId(order.getShopId())
                .createdAt(LocalDateTime.now())
                .items(order.getItems()).build();
    }

    public Order toOrder(BigDecimal total, UUID shopId) {
        return Order.builder()
                .customerId(null)
                .shopId(shopId)
                .status(OrderStatus.DRAFF)
                .totalAmount(total)
                .reference(String.valueOf("REF" + UUID.randomUUID()).toUpperCase())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<OrderResponse> mapToResponse(List<Order> orders) {
        log.info("Converting order list to order response list");
        return orders.stream().map(
                order -> OrderResponse
                        .builder()
                        .customerId(order.getCustomerId())
                        .shopId(order.getShopId())
                        .status(order.getStatus())
                        .totalAmount(order.getTotalAmount())
                        .reference(order.getReference())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).toList();
    }


}
