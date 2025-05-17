package com.example.libararysystem.dto.AuthorDTO;

import lombok.Data;

@Data
public class AuthorCreateDTO {
    private String firstName;
    private String lastName;
    private String nationality;
    private int birthYear;


    public AuthorCreateDTO(String firstName, String lastName, String nationality, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.birthYear = birthYear;
    }
}
