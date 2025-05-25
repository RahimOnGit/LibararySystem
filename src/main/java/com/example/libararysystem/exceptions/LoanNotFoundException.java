package com.example.libararysystem.exceptions;

public class LoanNotFoundException extends RuntimeException
{
    public LoanNotFoundException(Long loanId)
    {
        super("Loan not found with id: " + loanId);
    }
}
