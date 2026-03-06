package com.sidof.shop_service.service;


import com.sidof.shop_service.model.Shop;
import com.sidof.shop_service.repository.ShopRepository;
import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.user.UserClient;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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
 * Since    : 12/16/25
 * </blockquote></pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;
    private final ShopMapperService shopMapperService;
    private final MerchantService    merchantService;
    private final UserClient userClient;

    public ShopResponse saveNewShop(ShopsRequest shopsRequest, Authentication authentication) throws IllegalAccessException {

        if (!authentication.isAuthenticated()) {
            throw new IllegalAccessException("He make sure you are connected !");
        }
        var connectedUserEmail = authentication.getName();
        log.info("Current logged in user email : {}", connectedUserEmail);
        var user = userClient.findUserByEmail(connectedUserEmail).orElseThrow(() -> new RuntimeException("User not found in Auth Service for email: " + connectedUserEmail));

        var merchantToSave = shopMapperService.toMerchant(user,shopsRequest.name());
        var merchantSaved = merchantService.save(merchantToSave);

        var shopToSave = shopMapperService.toShop(shopsRequest,user,merchantSaved);

        var existShop = shopRepository.findByEmail(shopsRequest.email()).isPresent();
        if (existShop) {
            log.error("Shop already exists : {}", shopsRequest.email());
            throw new EntityExistsException("Shop with email: " + shopsRequest.email() + " already exists");
        }
        log.info("Saving new shop {}",shopToSave);
        var  shopSaved = shopRepository.save(shopToSave);
        return shopMapperService.toShopResponse(shopSaved);
    }


    public ShopResponse getShop(UUID shopId) {
        var shop = shopRepository.findById(shopId).orElseThrow(() -> {
            log.error("Shop with ID {} does not exist", shopId);
            return new EntityNotFoundException(String.format("Shop with that ID %s does exist", shopId));
        });
        return shopMapperService.toShopResponse(shop);
    }

    public ShopResponse getShopByEmail(String email) {
        var shop = shopRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Shop with Email {} does not exist", email);
            return new EntityNotFoundException(String.format("Shop with that Email %s does exist", email));
        });
        return shopMapperService.toShopResponse(shop);
    }

    public List<Shop> getShops() {
        return shopRepository.findAll(Sort.by( "createdAt").descending());
//        return shopRepository.findAll();
    }


    public String extractEmailFromToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        return jwt.getClaim("user_email").toString();
    }
}
