package com.example.libararysystem.dto.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String Registration;

    public UserCreateDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.Registration = LocalDate.now().toString();
    }
}
