package com.example.libararysystem.dto.user;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
//    private String password;
//    private String Registration;


    public UserDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDTO() {

    }
}
