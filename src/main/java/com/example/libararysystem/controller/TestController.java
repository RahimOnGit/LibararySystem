package com.example.libararysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String test(Principal principal) {
        if (principal == null) return "Not authenticated";
        return "User Authenticated as: " + principal.getName();
    }


}
