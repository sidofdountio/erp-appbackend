package com.bis.app.service;

import com.bis.app.customer.CustomerClient;
import com.bis.app.kafka.OrderProducer;
import com.bis.app.model.Order;
import com.bis.app.model.OrderItem;
import com.bis.app.model.PaymentMethod;
import com.bis.app.payment.PaymentClient;
import com.bis.app.repository.OrdersRepository;
import com.bis.app.kafka.OrderConfirmation;
import com.bis.app.request.OrderLineRequest;
import com.bis.app.request.OrderRequest;
import com.bis.app.request.PaymentRequest;
import com.bis.app.response.CustomerResponse;
import com.bis.app.response.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository orderRepository;

    private final CustomerClient customerClient;
    private final OrderMapperService orderMapper;
    private final OrderItemService orderItemService;

    private  final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    @Transactional
    public OrderResponse createOrder(OrderRequest request, Authentication authentication) {

//        Check if shop exist

        var customer = getCustomer(authentication, request.customerId());

        var PENDING_ORDER = orderRepository.save(orderMapper.createOrder(request));

        // Link items to order
        List<OrderItem> orderItems = PENDING_ORDER.getItems();

        orderItems.forEach(item -> orderItemService.saveOrderItem(new OrderLineRequest(item.getProductId(), PENDING_ORDER.getId(), item.getQuantity(), item.getPriceAtPurchase())));

        PENDING_ORDER.setItems(orderItems);

//        Request payment
        var paymentRequest = new PaymentRequest(
                request.totalAmount(),
                PaymentMethod.CASH,
                customer,
                PENDING_ORDER.getId(),
                PENDING_ORDER.getReference()
        );

//        Process to payment. For now we are using Open Feign Client to trigger payment endpoind.
//        Todo: We will like to trigger it UI(When user will complete the payment process)
        paymentClient.requestOrderPayment(paymentRequest);

//        Send confirmation
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        PENDING_ORDER.getReference(),
                        PENDING_ORDER.getTotalAmount(),
                        PaymentMethod.CASH,
                        customer,
                        orderItems
                )
        );

        return orderMapper.mapToResponse(PENDING_ORDER);
    }

    public OrderResponse finalizeMyOrder(Order orderToFinalize) {

        return null;
    }

    public CustomerResponse getCustomer(Authentication authentication, UUID customerId) {
        if (authentication.isAuthenticated()) {
            var email = authentication.getName();
        }
        var customer = customerClient.getCustomer(customerId).get();

        return CustomerResponse.builder()
                .id(customer.getId()).address(customer.getAddress()).createdBy(customer.getCreatedBy()).build();
    }


    public List<OrderResponse> findAllOrder() {
        log.info("Fetching all orders");
        var orders = orderRepository.findAll();
        return  orderMapper.mapToResponse(orders);
    }
}
