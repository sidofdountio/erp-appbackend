package com.sidof.app.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 1/18/26
 * </blockquote></pre>
 */

@Builder @Getter @Setter
public class UserResponse {
    private Long id;
    private String userUuid;
    private String email;
    private String firstName;
    private String lastName;
    private String username;

    private boolean enable;
    private boolean accountLocked;

    private boolean mfa ;
    private boolean mfaVerified ;
    private String mfaSecret;


    private int failedLoginAttempts;
    private LocalDateTime lastLogin;
}
