package com.sidof.app.config;


import com.sidof.app.model.User;
import com.sidof.app.model.Permission;
import com.sidof.app.model.Role;
import com.sidof.app.repository.PermissionRepository;
import com.sidof.app.repository.RoleRepository;
import com.sidof.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Set;

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

    @Value("${ui.app.url}")
    private String redirectUri;

    @Bean
    CommandLineRunner init() {
        return args -> {
            createDefaultRegisterClient();

            // 1. Create Permissions
            Permission userRead = createPermission("USER_READ");
            Permission userUpdate = createPermission("USER_UPDATE");
            Permission userDelete = createPermission("USER_DELETE");
            Permission productCreate = createPermission("PRODUCT_CREATE");
            Permission SHOP_READ = createPermission("SHOP_READ");
            Permission SHOP_WRITE = createPermission("SHOP_WRITE");
            Permission ORDER_CREATE = createPermission("ORDER_CREATE");
            Permission ORDER_READ = createPermission("ORDER_READ");

            // 2. Create Roles
            Role userRole = createRole("USER", Set.of(userRead));
            Role adminRole = createRole("ADMIN", Set.of(userRead, userUpdate, userDelete, productCreate));

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


    private void createDefaultRegisterClient() {
        // 1. Check if client already exists
        if (registeredClientRepository.findByClientId("backend-client") == null) {
            try {
                var backendRegisterClient = RegisteredClient.withId(randomUUID().toString())
                        .clientId("backend-client").clientSecret(passwordEncoder.encode("backend-secret"))
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


        if (registeredClientRepository.findByClientId("shop-client") == null) {
            try {
                RegisteredClient angularClient = RegisteredClient.withId(randomUUID().toString())
                        .clientId("shop-client")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantTypes(grants -> {
                            grants.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            grants.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })
                        .redirectUri("http://localhost:3000/callback")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope("SHOP_READ")
                        .scope("SHOP_WRITE")
                        .scope("ORDER_READ")
                        .scope("ORDER_UPDATE")
                        .scope("PRODUCT_READ")
                        .scope("PRODUCT_CREATE")
                        .clientSettings(ClientSettings.builder()
                                .requireAuthorizationConsent(true)
                                .requireProofKey(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofDays(1))
                                .refreshTokenTimeToLive(Duration.ofDays(30))
                                .build())
                        .build();

                registeredClientRepository.save(angularClient);
            } catch (Exception exception) {
                log.error(exception.getMessage());
            }
        }


        if (registeredClientRepository.findByClientId("admin-dashboard") == null) {
            try {
                RegisteredClient admin = RegisteredClient.withId(randomUUID().toString())
                        .clientId("admin-dashboard")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                        .authorizationGrantTypes(grants -> {
                            grants.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            grants.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })
                        .redirectUri("http://localhost:3000/callback")
                        .scope(OidcScopes.OPENID)
                        .scope(OidcScopes.PROFILE)
                        .scope(OidcScopes.EMAIL)
                        .scope("SHOP_READ")
                        .scope("SHOP_WRITE")
                        .scope("ADMIN_READ")
                        .scope("ADMIN_WRITE")
                        .scope("USER_MANAGEMENT")
                        .clientSettings(ClientSettings.builder()
                                .requireAuthorizationConsent(false)
                                .requireProofKey(true)
                                .build())
                        .tokenSettings(TokenSettings.builder()
                                .accessTokenTimeToLive(Duration.ofMinutes(5))
                                .build())
                        .build();

                registeredClientRepository.save(admin);
            } catch (Exception exception) {
                log.error(exception.getMessage());
            }

            // ---  MOBILE APP (Native iOS/Android) ---
            // Special redirect URIs (Custom Schemes) and longer refresh cycles.
            if (registeredClientRepository.findByClientId("mobile-app") == null) {
                try{
                    RegisteredClient mobileClient = RegisteredClient.withId(randomUUID().toString())
                            .clientId("mobile-app")
                            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                            .redirectUri("http://localhost:3000/callback`   1   ") // Custom URI scheme
                            .scope(OidcScopes.OPENID)
                            .scope(OidcScopes.PROFILE)
                            .scope(OidcScopes.EMAIL)
                            .scope("SHOP_READ")
                            .scope("USER_READ")
                            .scope("PRODUCT_READ")
                            .scope("ORDER_WRITE")
                            .clientSettings(ClientSettings.builder()
                                    .requireProofKey(true)
                                    .build())
                            .tokenSettings(TokenSettings.builder()
                                    .accessTokenTimeToLive(Duration.ofHours(1))
                                    .reuseRefreshTokens(true)
                                    .build())
                            .build();
                    registeredClientRepository.save(mobileClient);

                } catch (Exception exception) {
                    log.error("An error occured while saving mobile register client {}",exception.getMessage());
                }
            }

            // Special redirect URIs (Custom Schemes) and longer refresh cycles.
            if (registeredClientRepository.findByClientId("web-app") == null) {
                try{
                    RegisteredClient mobileClient = RegisteredClient.withId(randomUUID().toString())
                            .clientId("web-app")
                            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                            .redirectUri("http://localhost:3000/callback") // Custom URI scheme
                            .scope(OidcScopes.OPENID)
                            .scope(OidcScopes.PROFILE)
                            .scope(OidcScopes.EMAIL)
                            .scope("SHOP_READ")
                            .scope("USER_READ")
                            .scope("PRODUCT_READ")
                            .scope("ORDER_WRITE")
                            .clientSettings(ClientSettings.builder()
                                    .requireProofKey(true)
                                    .requireAuthorizationConsent(true)
                                    .build())
                            .tokenSettings(TokenSettings.builder()
                                    .accessTokenTimeToLive(Duration.ofDays(5))
                                    .refreshTokenTimeToLive(Duration.ofDays(30))
                                    .build())
                            .build();
                    registeredClientRepository.save(mobileClient);

                } catch (Exception exception) {
                    log.error("An error occured while saving web register client {}",exception.getMessage());
                }
            }
        }

    }
}