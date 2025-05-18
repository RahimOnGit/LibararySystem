package com.example.libararysystem.dto.loan;

import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoanDTO {

    private Book book;
    private User user;
private String borrowDate;
private String dueDate;
private String returnedDate;



    public LoanDTO(String borrowDate, String dueDate, String returnedDate, Book book, User user) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;

    }
    public LoanDTO() {}
}
