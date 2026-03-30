package com.sidof.shop_service.service;

import com.sidof.shop_service.model.Merchant;
import com.sidof.shop_service.model.Shop;
import com.sidof.shop_service.request.ShopsRequest;
import com.sidof.shop_service.response.ShopResponse;
import com.sidof.shop_service.response.UserResponse;
import jakarta.validation.constraints.NotBlank;
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
                .userId(shop.getMerchant().getUserId())
                .contactPhone(shop.getPhoneNumber())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .build();
    }

    public Shop toShop(ShopsRequest request, UserResponse use,Merchant merchant){
        return Shop.builder()
                .name(request.name())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .city(request.city())
                .address(request.address())
                .merchant(merchant)
                .shopOpen(true)
                .locked(false)
                .latitude(request.latitude())
                .longitude(request.longitude())
                .build();
    }

    public Merchant toMerchant(UserResponse user, String name) {
        return Merchant.builder()
                .businessName(name)
                .username(user.getUsername())
                .email(user.getEmail())
                .active(true)
                .userId(user.getId())
                .build();
    }
}
