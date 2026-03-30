package com.sidof.shop_service.controller;


import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/16/25
 * </blockquote></pre>
 */

@RestController
@RequestMapping("/api/v1/bis/public/shops")
@RequiredArgsConstructor
public class PublicShopsController {
    private final ShopService shopService;


    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponse> getShop(@PathVariable UUID shopId) {
        return new ResponseEntity<>(shopService.getShop(shopId), HttpStatus.CREATED);
    }


}
