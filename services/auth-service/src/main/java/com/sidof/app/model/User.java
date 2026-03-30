package com.sidof.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User implements UserDetails, Principal {


    @Id
    @SequenceGenerator(name = "users_id_sequence", allocationSize = 1, sequenceName = "users_id_sequence")
    @GeneratedValue(generator = "users_id_sequence", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userUuid;


    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;


    @Column(unique = true,nullable = false)
    private String username;

    private boolean enable=true;
    private boolean accountLocked = false;


    private boolean mfa = false;
    private boolean mfaVerified = false;       // MFA completed during login
    private String mfaSecret; // For TOTP (Google Authenticator)


    private int failedLoginAttempts = 0; // For account lock after max attempts
    private LocalDateTime lastLogin;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;


    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (role != null && role.getPermissions() != null) {
            for (Permission permission : role.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }



    public String getFullName() {
        return (firstName != null ? firstName : "") + "  " + (lastName != null ? lastName : "");
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userUuid='" + userUuid + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", failedLoginAttempts=" + failedLoginAttempts +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
