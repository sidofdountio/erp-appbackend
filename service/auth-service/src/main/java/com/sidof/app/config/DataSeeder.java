package com.sidof.app.config;


import com.sidof.app.model.Permission;
import com.sidof.app.model.Role;
import com.sidof.app.model.User;
import com.sidof.app.repository.PermissionRepository;
import com.sidof.app.repository.RoleRepository;
import com.sidof.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/8/25
 * </blockquote></pre>
 */


@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegisteredClientRepository registeredClientRepository;

    @Value("${ui.app-shop.url}")
    private String redirectUri;

    @Bean
    CommandLineRunner init() {
        return args -> {

            createDefaultRegisterClient();

            System.out.println("=================================================================");
            System.out.println(redirectUri);
            System.out.println("=================================================================");
            // 1. Create Permissions
            Permission userRead = createPermission("USER_READ");
            Permission userUpdate = createPermission("USER_UPDATE");
            Permission userDelete = createPermission("USER_DELETE");

            Permission productCreate = createPermission("PRODUCT_CREATE");
            Permission productUpdate = createPermission("PRODUCT_UPDATE");
            Permission productRead = createPermission("PRODUCT_READ");

            Permission SHOP_READ = createPermission("SHOP_READ");
            Permission SHOP_WRITE = createPermission("SHOP_WRITE");
            Permission ORDER_CREATE = createPermission("ORDER_CREATE");
            Permission ORDER_READ = createPermission("ORDER_READ");

            // 2. Create Roles
            Role userRole = createRole("USER", Set.of(userRead));
            Role adminRole = createRole("ADMIN", Set.of(userRead, userUpdate, userDelete, productCreate, SHOP_READ, SHOP_WRITE, ORDER_CREATE));
            Role adminRole2 = createRole("ADMIN", Set.of(productCreate, SHOP_WRITE));

            Role managerRole = createRole("MANAGER", Set.of(userRead, userUpdate, userDelete, productCreate, productRead));
            Role merchantRole = createRole("MERCHANT", Set.of(userRead, userUpdate, userDelete, productCreate));

            Role systemRole = createRole("SYSTEM", Set.of(userRead, userUpdate, userDelete, productCreate, productRead));

            // 3. Create Admin User
            if (userRepository.findByEmail("admin@manager.com").isEmpty()) {
                User admin = new User();
                admin.setUserUuid(randomUUID().toString());
                admin.setUsername("admin21");
                admin.setFirstName("admin");
                admin.setLastName("admin");
                admin.setEmail("admin@manager.com");
                admin.setEnable(true);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(adminRole);
                admin.setRole(userRole);
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("admin@store.com").isEmpty()) {
                User admin = new User();
                admin.setUserUuid(randomUUID().toString());
                admin.setUsername("admin");
                admin.setFirstName("Doe");
                admin.setLastName("admin");
                admin.setEmail("admin@store.com");
                admin.setEnable(true);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(adminRole2);
                userRepository.save(admin);
            }


            if (userRepository.findByEmail("user@manager.com").isEmpty()) {
                User user = new User();
                user.setUserUuid(randomUUID().toString());
                user.setUsername("user11");
                user.setFirstName("user");
                user.setLastName("user");
                user.setEmail("user@manager.com");
                user.setEnable(true);
                user.setPassword(passwordEncoder.encode("user1122"));
                user.setRole(userRole);
                userRepository.save(user);
            }

            if (userRepository.findByEmail("user@store.com").isEmpty()) {
                User userStore = new User();
                userStore.setUserUuid(randomUUID().toString());
                userStore.setUsername("store11");
                userStore.setFirstName("store");
                userStore.setLastName("store");
                userStore.setEmail("user@store.com");
                userStore.setEnable(true);
                userStore.setPassword(passwordEncoder.encode("password"));
                userStore.setRole(userRole);
                userRepository.save(userStore);
            }
        };

    }

    private Permission createPermission(String name) {
        return permissionRepository.findByName(name).orElseGet(() -> permissionRepository.save(new Permission(null, name)));
    }

    private Role createRole(String RoleName, Set<Permission> permissions) {
        return roleRepository.findByName(RoleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(RoleName);
            role.setPermissions(permissions);
            return roleRepository.save(role);
        });
    }

    @Transactional
//    @EventListener(ApplicationReadyEvent.class)
    private void createDefaultRegisterClient() {
        // 1. Check if client already exists
        if (registeredClientRepository.findByClientId("backend-client") == null) {
            try {
                var backendRegisterClient = RegisteredClient.withId(randomUUID().toString())
                        .clientId("backend-client")
                        .clientSecret(passwordEncoder.encode("backend-secret"))
                        .authorizationGrantTypes(types -> {
                            types.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            types.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })
                        .redirectUri("http://localhost:3000/callback")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("read")
                        .scope("write")
                        .scope("SHOP_READ")

                        .tokenSettings(TokenSettings.builder()
                                .refreshTokenTimeToLive(Duration.ofHours(1))
                                .accessTokenTimeToLive(Duration.ofDays(15))
                                .build()).build();

                registeredClientRepository.save(backendRegisterClient);
            } catch (Exception exception) {
                log.error(exception.getMessage());
            }
        }


        // --- 1. ADMIN DASHBOARD (Internal Management Tool) ---
        if (registeredClientRepository.findByClientId("admin") == null) {
            try {
                RegisteredClient ADMIN_APP = RegisteredClient.withId(randomUUID().toString())
                        .clientId("admin")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE) // Public client (React/Angular)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:3004/callback")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("ADMIN_READ")
                        .scope("ADMIN_WRITE")
                        .scope("USER_MANAGEMENT")
                        .clientSettings(ClientSettings.builder()
                                .requireProofKey(true)
                                .requireAuthorizationConsent(false)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofHours(2))
                                .refreshTokenTimeToLive(Duration.ofDays(1))
                                .build())
                        .build();
                registeredClientRepository.save(ADMIN_APP);
            } catch (Exception e) {
                log.error("Error creating admin-dashboard client: {}", e.getMessage());
            }
            }

        // --- 2. MOBILE APP
        if (registeredClientRepository.findByClientId("mobile") == null) {
            try {
                RegisteredClient mobile = RegisteredClient.withId(String.valueOf(UUID.randomUUID()))
                            .clientId("mobile")
                            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        // Note: Mobile often uses custom schemes like myapp://callback
                        .redirectUri("http://localhost:3000/callback")
                            .scope(OidcScopes.OPENID)
                            .scope("SHOP_READ")
                            .scope("PRODUCT_READ")
                            .scope("ORDER_WRITE")
                            .clientSettings(ClientSettings.builder()
                                    .requireProofKey(true)
                                    .build())
                            .tokenSettings(TokenSettings.builder()
                                    .accessTokenTimeToLive(Duration.ofDays(1)) // Mobile users hate logging in every day
                                    .refreshTokenTimeToLive(Duration.ofDays(90)) // Very long refresh for "Remember Me"
                                    .reuseRefreshTokens(true)
                                    .build())
                            .build();
                registeredClientRepository.save(mobile);
            } catch (Exception e) {
                log.error("Error creating mobile-app client: {}", e.getMessage());
            }
        }


        // --- 3. SHOP FRONTEND APP
        if (registeredClientRepository.findByClientId("shop") == null) {
            try {
                RegisteredClient SHOP = RegisteredClient.withId(String.valueOf(UUID.randomUUID()))
                        .clientId("shop")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .redirectUri("http://localhost:3001/callback")
                        .scope(OidcScopes.OPENID)
                        .scope("SHOP_READ")
                        .scope("SHOP_CREATE")
                        .scope("PRODUCT_READ")
                        .scope("PRODUCT_CREATE")
                        .scope("ORDER_CREATE")
                        .scope("ORDER_READ")
                        .clientSettings(ClientSettings.builder()
                                .requireProofKey(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofDays(1)) // Mobile users hate logging in every day
                                .refreshTokenTimeToLive(Duration.ofDays(90)) // Very long refresh for "Remember Me"
                                .reuseRefreshTokens(true)
                                .build())
                        .build();
                registeredClientRepository.save(SHOP);
            } catch (Exception e) {
                log.error("Error creating shop-app client: {}", e.getMessage());
            }

        }

    }
}