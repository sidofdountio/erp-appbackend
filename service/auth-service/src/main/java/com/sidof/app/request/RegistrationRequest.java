package com.sidof.app.request;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Licence   : All Right Reserved BIS
 * Since    : 12/8/25
 * </blockquote></pre>
 */

public record RegistrationRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {}

