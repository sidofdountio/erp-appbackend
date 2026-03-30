package com.sidof.app.service;

import com.sidof.app.model.User;
import com.sidof.app.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 2/28/26
 * </blockquote></pre>
 */

@Service
@Slf4j
public class UserMapperService {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId()).userUuid(user.getUserUuid()).email(user.getEmail()).firstName(user.getFirstName()).lastName(user.getLastName()).username(user.getUsername()).enable(user.isEnable()).accountLocked(user.isAccountLocked()).mfa(user.isMfa()).mfaVerified(user.isMfaVerified()).mfaSecret(user.getMfaSecret()).lastLogin(user.getLastLogin())
                .build();
    }
}
