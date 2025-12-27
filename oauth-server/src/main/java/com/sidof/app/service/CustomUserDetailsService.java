package com.sidof.app.service;

import com.sidof.app.model.Permission;
import com.sidof.app.model.User;
import com.sidof.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 12/27/25
 * </blockquote></pre>
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Force Hibernate to load the permissions and convert to a standard HashSet
        if (user.getRole() != null && user.getRole().getPermissions() != null) {
            Set<Permission> cleanPermissions = new HashSet<>(user.getRole().getPermissions());
            user.getRole().setPermissions(cleanPermissions);
        }

        return user;
    }
}