package com.example.libararysystem.utilities;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Validator {

public List<String> validatePassword(String password) {

    List<String> errors = new ArrayList<String>();
    if (password.length() < 8) {
        errors.add("Password must be at least 8 characters");

    }
    if (!password.matches(".*[A-Z].*")) {
        errors.add("Password must contain at least one uppercase letter");
    }
    if (!password.matches(".*[0-9].*")) {
        errors.add("Password must contain at least one digit");
    }
    if (!password.matches("^[a-zA-Z0-9_]*$"))
    {        errors.add("Password must contain only letters ,  numbers and underscores ");
    }
return errors;
}




}
