package com.example.libararysystem.dto.loan;

import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanCreateDTO {

    private int bookId;
    private int userId;
    private String borrowDate;
    private String dueDate ;


    public LoanCreateDTO(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = LocalDate.now().toString();
        this.dueDate = LocalDate.now().plusMonths(1).toString();


    }



}
