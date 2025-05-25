package com.example.libararysystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;  
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class BookExceptionHandler {
private Error error;

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleBookNotFound(BookNotFoundException exception)
    {
        return new Error(
                "BOOK NOT FOUND",
                exception.getMessage()
        );

    }

    @ExceptionHandler(LoanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleLoanNotFound(LoanNotFoundException exception)
    {
        return new Error("LOAN NOT FOUND", exception.getMessage());

    }




}
