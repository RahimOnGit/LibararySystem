package com.example.libararysystem.service;

import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        User u = userRepo.findByUsername(usernameOrEmail)
                .orElseGet(() -> userRepo.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail)));

        String role = u.getRole(); // could already be "ROLE_ADMIN" or "ADMIN"
        // Ensure role has ROLE_ prefix for Spring Security
        String authority;
        if (role != null && role.startsWith("ROLE_")) {
            authority = role;
        } else if (role != null) {
            authority = "ROLE_" + role;
        } else {
            authority = "ROLE_USER"; // default role
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername() != null ? u.getUsername() : u.getEmail())
                .password(u.getPassword()) // must be encoded already (bcrypt)
                .authorities(authority)
                .disabled(!u.isEnabled())
                .build();
    }
}
