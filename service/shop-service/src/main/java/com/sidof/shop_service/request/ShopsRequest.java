package com.sidof.shop_service.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
        @NotBlank(message = "Shop name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Phone number is required")
        @Size(min = 9, max = 9, message = "Phone number must be exactly 9 digits")
        String phoneNumber,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Address is required")
        String address,

        String latitude,
        String longitude

) {

}
