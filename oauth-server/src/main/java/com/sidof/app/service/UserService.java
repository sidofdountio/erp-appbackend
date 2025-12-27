package com.sidof.app.service;


import com.sidof.app.model.User;
import com.sidof.app.request.RegistrationRequest;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/2/25
 * </blockquote></pre>
 */


public interface UserService {
    User register(RegistrationRequest request);

    User getUserByEmail(String email);

    void resetLoginAttempts(String userUuiId);

    void updateLoginAttempts(String email);

    void setLastLogin(Long userId);

    void addLoginDevice(Long userId, String deviceName, String client, String ipAddress);

    boolean verifyQrCode(String userUuiId, String code);

    boolean isMfaEnabled(Long userId);


}
