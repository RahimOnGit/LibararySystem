package com.example.libararysystem.controller;

import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateDTO dto) {
        try {
            if(dto.getPassword()==null || dto.getPassword().length()<3)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be at least 3 characters long");



            userService.register(dto);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserCreateDTO dto) {
        try {
            userService.registerAdmin(dto);
            return ResponseEntity.ok("Admin registered successfully!");
        }
        catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

