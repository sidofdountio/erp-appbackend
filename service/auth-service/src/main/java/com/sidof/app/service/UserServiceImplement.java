package com.sidof.app.service;

import com.sidof.app.exceptions.UserException;
import com.sidof.app.model.User;
import com.sidof.app.repository.UserRepository;
import com.sidof.app.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/17/26
 * </blockquote></pre>
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplement {
    private final UserRepository userRepository;
    private final UserMapperService userMapperService;

    public UserResponse getUser(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User does match with these provide ID {}", userId);
            return new UserException(String.format("User does match with these provide ID %S", userId));
        });
        log.info("Fetching user by ID {}", userId);
        return UserResponse.builder()
                .id(user.getId()).userUuid(user.getUserUuid()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).username(user.getFullName()).enable(user.isEnable()).accountLocked(user.isAccountLocked()).mfa(user.isMfa()).mfaVerified(user.isMfaVerified()).mfaSecret(user.getMfaSecret()).lastLogin(user.getLastLogin())
                .build();

    }

    public UserResponse getUserById(String userId) {
        var user = userRepository.findByUserUuid(userId).orElseThrow(() -> {
            log.error("User does match with these provide UUID {}", userId);
            return new UserException(String.format("User does match with these provide ID %S", userId));
        });
        log.info("Fetching user by UUID {}", userId);
        return UserResponse.builder()
                .id(user.getId()).userUuid(user.getUserUuid()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).username(user.getFullName()).enable(user.isEnable()).accountLocked(user.isAccountLocked()).mfa(user.isMfa()).mfaVerified(user.isMfaVerified()).mfaSecret(user.getMfaSecret()).lastLogin(user.getLastLogin())
                .build();

    }


    public UserResponse getUserByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User does match with these provide EMAIL {}", email);
            return new UserException(String.format("User does match with these provide EMAIL %S", email));
        });
        log.info("Fetching user by EMAIL {}", email);
        return UserResponse.builder()
                .id(user.getId()).userUuid(user.getUserUuid()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).username(user.getFullName()).enable(user.isEnable()).accountLocked(user.isAccountLocked()).mfa(user.isMfa()).mfaVerified(user.isMfaVerified()).mfaSecret(user.getMfaSecret()).lastLogin(user.getLastLogin())
                .build();

    }

    public UserResponse getUserByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User does match with these provide USERNAME {}", username);
            return new UserException(String.format("User does match with these provide USERNAME %S", username));
        });
        log.info("Fetching user by USERNAME {}", username);
        return userMapperService.toUserResponse(user);

    }

    private User getUserUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("User does match with these provide USERNAME {}", username);
            return new UserException(String.format("User does match with these provide USERNAME %S", username));
        });
        log.info("Fetching user by USERNAME {}", username);
        return user;

    }


    public UserResponse getUserProfile(Authentication authentication) {
        var username = authentication.getName();
        var user = getUserUsername(username);
        log.info("Fetching user by profile {}", username);
        return userMapperService.toUserResponse(user);
    }
}
