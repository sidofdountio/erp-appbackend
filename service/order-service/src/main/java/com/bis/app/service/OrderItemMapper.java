package com.bis.app.service;

import com.bis.app.model.Order;
import com.bis.app.model.OrderItem;
import com.bis.app.product.ProductClient;
import com.bis.app.request.OrderLineRequest;
import com.bis.app.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
@RequiredArgsConstructor
@Slf4j
public class OrderItemMapper {

    private final ProductClient productClient;

    public List<OrderItem> toOrderItem(OrderRequest request) {
        log.info("Generating order item entity");
        return request.items().stream().map(itemRequest -> {
            // Call Product Service to get current price and validate stock
            var productInfo = productClient.getProductById(itemRequest.productId()).get();

            return OrderItem.builder()
                    .productId(productInfo.getId())
                    .quantity(itemRequest.quantity())
                    .priceAtPurchase(productInfo.getPrice())
                    .build();
        }).toList();
    }

    public OrderItem toOrderItem(OrderLineRequest request) {
        log.info("Generating order item entity");
        return OrderItem.builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .priceAtPurchase(request.priceAtPurchase())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .build();
    }




}
