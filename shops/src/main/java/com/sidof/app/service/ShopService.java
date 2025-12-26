package com.sidof.app.service;


import com.sidof.app.model.Shop;
import com.sidof.app.request.ShopsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


    public Shop saveNewShop(ShopsRequest shopsRequest) {
        var shopToSave = Shop.builder().name(shopsRequest.name()).code(shopsRequest.code()).build();

        return shopToSave;
    }
}
