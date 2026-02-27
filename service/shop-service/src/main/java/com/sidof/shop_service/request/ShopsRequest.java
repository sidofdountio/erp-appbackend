package com.sidof.shop_service.request;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 11/26/25
 * </blockquote></pre>
 */

public record ShopsRequest(
        String name,
        String email,
        String code,
        String latitude,
        String longitude

) {

}
