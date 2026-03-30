package com.bis.app.response;

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
 * Since    : 1/16/26
 * </blockquote></pre>
 */


@Builder @Data
public class CustomerResponse {

    private UUID id;
    private String address;
    private String createdBy;
}
