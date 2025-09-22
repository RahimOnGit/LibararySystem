package com.example.libararysystem.dto.loan;

import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LoanDTO {

    private String book_title;
    private String username;
private String borrowDate;
private String dueDate;
private String returnedDate;



    public LoanDTO(String borrowDate, String dueDate, String returnedDate, String book_title, String username) {
        this.book_title = book_title;
        this.username = username;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;

    }
    public LoanDTO() {}


}
