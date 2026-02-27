package com.bis.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/16/25
 * </blockquote></pre>
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors->cors.configurationSource(corsConfigurationSource()));
        http
                .authorizeHttpRequests((authorize) -> authorize

                        .requestMatchers(HttpMethod.GET,"/api/v1/product/**").hasAuthority("SCOPE_PRODUCT_READ")
                        .requestMatchers(HttpMethod.GET,"/api/v1/category/**").hasAuthority("SCOPE_PRODUCT_READ")

                        .requestMatchers(HttpMethod.POST,"/api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/api/v1/category/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/product/**").hasRole("ADMIN")

                        .requestMatchers("/actuator/**","/test/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }


    // ========================= FULL CORS CONFIGURATION =========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        var cors = new CorsConfiguration();

        cors.setAllowCredentials(true);

        cors.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:9001",
                "http://localhost:9000",
                "http://127.0.0.1:9000",
                "http://127.0.0.1:9001",
                "http://127.0.0.1:9002",
                "http://195.231.19.75:3000",
                "https://195.231.19.75:3000"
        ));

        cors.setAllowedHeaders(List.of(
                HttpHeaders.ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CACHE_CONTROL,
                HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
                HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
                "X-Requested-With",
                "Refresh-Token"
        ));

        cors.setExposedHeaders(List.of(
                HttpHeaders.ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                HttpHeaders.AUTHORIZATION,
                "Refresh-Token"
        ));

        cors.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.HEAD.name()
        ));

        cors.setMaxAge(3600L);
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
