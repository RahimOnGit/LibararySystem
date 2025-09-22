package com.example.libararysystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="user_id" , columnDefinition = "INTEGER")
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "registration_date")
    private String Registration;

    @Column(name = "username")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "username must be alphanumeric")
    private String username;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "role" , nullable = false)
    private String role;

    @Column(name="failed_attempts")
    private Integer failedAttempts = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

public User() {}

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }


}

