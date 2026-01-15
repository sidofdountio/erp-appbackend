package com.sidof.app.response;

import java.time.LocalDateTime;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1
 * Since    : 1/14/26
 * </blockquote></pre>
 */

public record CategoryResponse(
        String name,
        String description,
        LocalDateTime createdAt,
        String createdBy
) {
}
