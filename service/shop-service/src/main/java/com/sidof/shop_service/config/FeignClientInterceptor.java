package com.sidof.shop_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEBUG: Interceptor found authentication: " + (authentication != null));

        if (authentication == null) {
            return;
        }

        String tokenValue = null;

        // Case 1: The most common for Resource Servers
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            tokenValue = jwtAuth.getToken().getTokenValue();
        }
        // Case 2: If the principal itself is the Jwt object
        else if (authentication.getPrincipal() instanceof Jwt jwt) {
            tokenValue = jwt.getTokenValue();
        }
        // Case 3: Sometimes it's tucked away in the credentials
        else if (authentication.getCredentials() instanceof Jwt jwt) {
            tokenValue = jwt.getTokenValue();
        }

        if (tokenValue != null) {
            requestTemplate.header("Authorization", "Bearer " + tokenValue);
        }
    }
}
