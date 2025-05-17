package com.example.libararysystem.dto.AuthorDTO;

import lombok.Data;

@Data
public class AuthorDTO {

private String firstName;
private String lastName;


public AuthorDTO(String firstName, String lastName) {

    this.firstName = firstName;
    this.lastName = lastName;

}
}
