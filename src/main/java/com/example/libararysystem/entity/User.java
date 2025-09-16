package com.example.libararysystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

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

    @Column()
    private String username;

    @Column
    private boolean enabled = true;

    @Column(name = "role" , nullable = false)
    private String role;


    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

public User() {}

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}

