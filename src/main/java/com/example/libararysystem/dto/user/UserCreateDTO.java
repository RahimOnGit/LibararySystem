package com.example.libararysystem.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserCreateDTO {
    @NotBlank(message = "first name is required")
    private String firstName;
    @NotBlank(message = "last name is required")
    private String lastName;

    @Size(min = 3, message = "at least 3 characters")
    @NotBlank(message = "username name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_]*$", message = "username must be alphanumeric")
    private String username;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 4 , message = "password must be at least 4 characters")
     private String password;
    private String Registration = LocalDate.now().toString();

    private String role = "USER";


    public UserCreateDTO(String firstName, String lastName,String Username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = Username;
        this.email = email;
        this.password = password;
        this.Registration = LocalDate.now().toString();
        this.role = "USER";

    }

    public UserCreateDTO() {

    }
}
