package com.bis.app.customer;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 2/4/26
 * </blockquote></pre>
 */

public record Customer(
        String customerId,
        String firstName,
        String lastName,
        String email
) {
}
