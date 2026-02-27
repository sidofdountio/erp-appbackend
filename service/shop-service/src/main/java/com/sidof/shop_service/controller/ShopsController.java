package com.sidof.shop_service.controller;


import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/bis/shops")
@RequiredArgsConstructor
public class ShopsController {
    private final ShopService shopService;

    @PostMapping
    public ResponseEntity<ShopResponse> save(@RequestBody ShopsRequest shopsRequest,Authentication authentication) throws IllegalAccessException {
        return new ResponseEntity<>(shopService.saveNewShop(shopsRequest,authentication), HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponse> getShop(@PathVariable UUID shopId) {
        return new ResponseEntity<>(shopService.getShop(shopId), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ShopResponse> getShopByEmail(@PathVariable String email) {
        return new ResponseEntity<>(shopService.getShopByEmail(email), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<?> read(Authentication authentication) {
//        shopService.getAllShop();
        return new ResponseEntity<>("HELLO READ " + authentication.getAuthorities(), HttpStatus.OK);
    }
}
