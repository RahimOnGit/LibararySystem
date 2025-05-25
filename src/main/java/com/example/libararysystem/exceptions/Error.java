package com.example.libararysystem.exceptions;

import lombok.Data;

@Data
public class Error {


    private String error;
    private String message;

    Error(String error, String message) {
        this.error = error;
        this.message = message;
    }

}
