package com.example.libararysystem.exceptions;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(String title) {
        super("Book not found with title: " + title);

    }
    public BookNotFoundException(int id) {
        super("Book not found with id: " + id);
    }
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}


