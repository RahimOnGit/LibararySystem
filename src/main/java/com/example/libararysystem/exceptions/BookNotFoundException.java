package com.example.libararysystem.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String title) {
        super("Book not found with title: " + title);

    }
}


