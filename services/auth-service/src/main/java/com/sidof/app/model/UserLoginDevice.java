package com.sidof.app.model;

import jakarta.persistence.*;
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
 * Since    : 12/7/25
 * </blockquote></pre>
 */

@Getter
@Setter
@Entity
@Table(name = "user_login_devices")
public class UserLoginDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceName;       // e.g. "Samsung S24", "Macbook Pro"
    private String client;           // e.g. "Chrome", "Firefox", "Android App"
    private String ipAddress;

    private LocalDateTime loginAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}

