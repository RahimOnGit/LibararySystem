package com.example.libararysystem.service;

import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DataInitService {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    public DataInitService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct

    public void init() {
        try {
            System.out.println("Initializing data...");


            if (!userRepo.findByEmail("admin@gmail.com").isPresent()) {
                User admin = new User();
                admin.setFirstName("System");
                admin.setLastName("Administrator");
                admin.setEmail("admin@gmail.com");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRole("ADMIN");

                userRepo.save(admin);

                System.out.println("admin added admin@gmail.com /   admin123 / admin ");

            }
            System.out.println("data initialized completed successfully.");


        } catch (Exception e) {
            System.err.println("error during data init" + e.getMessage());

        }
    }
}
    