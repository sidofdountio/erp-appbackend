package com.sidof.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sidof.app.model.User;
import com.sidof.app.service.CustomUserDetailsService;
import com.sidof.app.utils.HibernatePersistentSetMixin;
import com.sidof.app.utils.PermissionMixin;
import com.sidof.app.utils.RoleMixin;
import com.sidof.app.utils.UserMixin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/25/25
 * </blockquote></pre>
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ========================= AUTHORIZATION SERVER =========================
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, auth -> auth.oidc(Customizer.withDefaults()))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML))
                );

        return http.build();
    }

    // ========================= DEFAULT SECURITY =========================
    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        // Link your DB-backed provider to this chain
        http.authenticationProvider(daoAuthenticationProvider);
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**","/error").permitAll()
                // Allow your User API to be accessed by internal services
                .requestMatchers("/api/v1/bis/users/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/logout").permitAll()
                .requestMatchers("/mfa").hasAuthority("MFA_REQUIRED")
                .anyRequest().authenticated()
        );

        // ADD THIS TO ALLOW THE AUTH SERVICE TO ACCEPT JWT TOKENS
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        http.formLogin(login -> login
                .loginPage("/login")
//                .successHandler(new MfaAuthenticationHandler("/mfa", "MFA_REQUIRED"))
                .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"))
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:9000/login?logout") // Redirect back to log in
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
        );

        return http.build();
    }


    // ========================= REGISTERED CLIENT (JDBC) =========================
    @Bean
    public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        JdbcOAuth2AuthorizationService service =
                new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);

        // This mapper supports .setObjectMapper()
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper =
                new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);

        rowMapper.setObjectMapper(createObjectMapperWithUserSupport());
        service.setAuthorizationRowMapper(rowMapper);

        return service;
    }

    @Bean
    public OAuth2AuthorizationConsentService authorizationConsentService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository
    ) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
    }

    private ObjectMapper createObjectMapperWithUserSupport() {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassLoader classLoader = JdbcOAuth2AuthorizationService.class.getClassLoader();

        // 1. Register Standard Security & Auth Server Modules
        objectMapper.registerModules(SecurityJackson2Modules.getModules(classLoader));
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());

        // 2. Register Hibernate Module (Tells Jackson how to read Hibernate proxies)
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module());

        // 3. REGISTER ALL MIXINS (The Allowlist)
        objectMapper.addMixIn(com.sidof.app.model.User.class, UserMixin.class);
        objectMapper.addMixIn(com.sidof.app.model.Role.class, RoleMixin.class);
        objectMapper.addMixIn(com.sidof.app.model.Permission.class, PermissionMixin.class);

        // THE CRITICAL ADDITION: Allow the Hibernate PersistentSet class
        objectMapper.addMixIn(org.hibernate.collection.spi.PersistentSet.class, HibernatePersistentSetMixin.class);

        return objectMapper;
    }



    // ========================= JWT / KEYS =========================
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }


    // ========================= USERS =========================

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }



    // ========================= CUSTOM JWT CLAIMS =========================

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> customizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {

                // 1. Get the authorities using your existing method

                List<String> authorities = context.getPrincipal()
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList();

                context.getClaims().claim("authorities", authorities);
//                context.getClaims().claims(claims ->
//                        claims.put("authorities", getAuthorities(context))
//                );

                // 2. Get the Principal and cast it to your AppUser
                Object principal = context.getPrincipal().getPrincipal();

                if (principal instanceof User user) {
                    context.getClaims().claims(claims -> {
                        claims.put("user_uuid", user.getUserUuid());
                        claims.put("full_name", user.getFullName());
                        claims.put("mfa_enabled", user.isMfa());
                        claims.put("user_email", user.getEmail());
                        claims.put("mfa_verified", user.isMfaVerified());
                    });
                }
            }
        };
    }


    private String getAuthorities(JwtEncodingContext context) {
        return context.getPrincipal().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailsService appUserDetailsService) {
        return appUserDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider daoAuthenticationProvider) {
        return new ProviderManager(daoAuthenticationProvider);
    }
























































    // ========================= FULL CORS CONFIGURATION =========================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        var cors = new CorsConfiguration();

        cors.setAllowCredentials(true);

        cors.setAllowedOrigins(List.of(
                "http://localhost:3010",
                "http://localhost:3011",
                "http://localhost:3012",
                "http://localhost:3013",
                "http://localhost:9000",
                "http://127.0.0.1:9000",
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
