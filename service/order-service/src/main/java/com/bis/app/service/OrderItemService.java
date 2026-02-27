package com.bis.app.service;

import com.bis.app.model.Order;
import com.bis.app.model.OrderItem;
import com.bis.app.product.ProductClient;
import com.bis.app.repository.OrderItemRepository;
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
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private OrderItemMapper orderItemMapper;

    public void saveOrderItem(OrderItem orderItem){
        log.info("Saving each item {} from order",orderItem);
       orderItemRepository.save(orderItem);
    }

    public void saveOrderItem(OrderLineRequest request){
        var orderItemToSave = orderItemMapper.toOrderItem(request);
        log.info("Saving each item {} from order",orderItemToSave);
        orderItemRepository.save(orderItemToSave);
    }












}
