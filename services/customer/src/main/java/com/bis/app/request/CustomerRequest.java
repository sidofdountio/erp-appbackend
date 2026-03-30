package com.bis.app.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/16/26
 * </blockquote></pre>
 */

public record CustomerRequest(
        @NotEmpty(message = "name can't be empty")
        @NotBlank(message = "name cam't be empty")
        String firstName,
        @NotEmpty(message = "name can't be empty")
        @NotBlank(message = "name cam't be empty")
        String lastName,
        @Email
        String email,
        String address
) {
}
