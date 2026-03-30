package com.bis.app.customer;

import jakarta.validation.constraints.Email;
import org.springframework.validation.annotation.Validated;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/1/26
 * </blockquote></pre>
 */

@Validated
public record Customer(
        String customerId,
        String firstName,
        String lastName,
        @Email(message = "Email not in the right format")
        String email
) {
}
