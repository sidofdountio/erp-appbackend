package com.sidof.shop_service.controller;


import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopsController {
    private final ShopService shopService;


    @PostMapping
    public ResponseEntity<?> save(@RequestBody ShopsRequest shopsRequest) {
        shopService.saveNewShop(shopsRequest);
        return new ResponseEntity<>("shop created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> read(Authentication authentication) {
//        shopService.getAllShop();
        return new ResponseEntity<>("HELLO READ " + authentication.getAuthorities(), HttpStatus.OK);
    }
}
