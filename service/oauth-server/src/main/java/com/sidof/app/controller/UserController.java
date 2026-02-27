package com.sidof.app.controller;

import com.sidof.app.response.UserResponse;
import com.sidof.app.service.UserServiceImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
@RequestMapping("/api/v1/bis/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImplement userServiceImplement;

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> user(@PathVariable Long userId) {
        return new ResponseEntity<>(userServiceImplement.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/uuid/{userUUID}")
    public ResponseEntity<UserResponse> userUUID(@PathVariable String userUUID) {
        return new ResponseEntity<>(userServiceImplement.getUserById(userUUID), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> userEmail(@PathVariable String email) {
        return new ResponseEntity<>(userServiceImplement.getUserByEmail(email), HttpStatus.OK);
    }
}
