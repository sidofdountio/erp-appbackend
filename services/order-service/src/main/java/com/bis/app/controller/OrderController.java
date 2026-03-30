package com.bis.app.controller;

import com.bis.app.request.OrderRequest;
import com.bis.app.response.OrderResponse;
import com.bis.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

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

@RestController
@RequestMapping("/api/v1/bis/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<OrderResponse>create(@RequestBody OrderRequest request, Authentication authentication){
        return new ResponseEntity<>(orderService.createOrder(request,authentication),CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>>orders(){
        return new ResponseEntity<>(orderService.findAllOrder(),CREATED);
    }
}
