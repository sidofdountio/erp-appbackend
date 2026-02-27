package com.bis.app.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/23/26
 * </blockquote></pre>
 */

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_TYPE = "Bearer";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 1. Retrieve the authentication object from the current Security Context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 2. Check if the user is authenticated via JWT
        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            // 3. Extract the token value
            String tokenValue = jwtToken.getToken().getTokenValue();

            // 4. Add the Header: "Authorization: Bearer <token>"
            requestTemplate.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, tokenValue));
        }
    }
}
