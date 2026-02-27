package com.sidof.shop_service.service;

import com.sidof.shop_service.model.Shop;
import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.response.UserResponse;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/27/26
 * </blockquote></pre>
 */

@Service
public class ShopMapperService {
    public ShopResponse toShopResponse(Shop shop){
        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .email(shop.getEmail())
                .build();
    }

    public Shop toShop(ShopsRequest request, UserResponse user){
        return Shop.builder()
                .name(request.name())
                .email(request.email())
                .latitude(request.latitude())
                .latitude(request.latitude())
                .userId(user.getId())
                .build();
    }
}
