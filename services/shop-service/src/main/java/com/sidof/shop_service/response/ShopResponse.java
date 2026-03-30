package com.sidof.shop_service.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

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


@Builder
@Data
public class ShopResponse {
    private UUID id;
    private String name;
    private String code;

    private String email;
    private String managerName;
    private String contactPhone;

//    private String categoryId;

    private String latitude;
    private String longitude;

    private Long userId;
}
