package com.sidof.shop_service.service;


import com.sidof.shop_service.model.Shop;
import com.sidof.shop_service.repository.ShopRepository;
import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.user.UserClient;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    private final UserClient userClient;

    public ShopResponse saveNewShop(ShopsRequest shopsRequest, Authentication authentication) throws IllegalAccessException {

        if (!authentication.isAuthenticated()) {
            throw new IllegalAccessException("He make sure you are connected !");
        }
        var connectedUserEmail = authentication.getName();
        var user = userClient.findUserByEmail(connectedUserEmail).get();

        // TODO: Associate the current creating store (store owner)
        var shopToSave = shopMapperService.toShop(shopsRequest,user);

        getShopByEmail(shopsRequest.email());
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
}
