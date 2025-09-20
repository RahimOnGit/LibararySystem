package com.example.libararysystem.controller;

import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.dto.user.UserLoginDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.service.LoginAttemptService;
import com.example.libararysystem.service.UserService;
import com.example.libararysystem.utilities.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final LoginAttemptService loginAttemptService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, LoginAttemptService loginAttemptService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.loginAttemptService = loginAttemptService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginRequest)
    {
        try{
            // get user and check lock
            Optional<User> optionalUser = userService.getUserByUsername(loginRequest.getUsername());
            User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));

            loginAttemptService.checkLock(user); // throws LockedException if locked

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername() , loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // on success reset attempts
            loginAttemptService.loginSucceeded(user);

            Optional<User> userOpt = userService.getUserByUsername(loginRequest.getUsername());

            String accessToken = jwtUtils.generateToken(userOpt.get());
            String refreshToken = jwtUtils.generateRefreshToken(loginRequest.getUsername());

            Map<String,String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);

            return ResponseEntity.ok(tokens);
        }
        catch (LockedException le) {
            return ResponseEntity.status(423).body("Account locked: " + le.getMessage());
        }
        catch (Exception e)
        {
            // on failure increment attempts if user exists
            try {
                Optional<User> maybe = userService.getUserByUsername(loginRequest.getUsername());
                maybe.ifPresent(u -> loginAttemptService.loginFailed(u));
            } catch (Exception ex) {
                // ignore
            }
            return  ResponseEntity.status(401).body("invalid username or password  - " +e.getMessage());
        }
    }


}