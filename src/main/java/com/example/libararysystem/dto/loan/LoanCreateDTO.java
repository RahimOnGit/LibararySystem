package com.example.libararysystem.dto.loan;

import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanCreateDTO {

    private long bookId;
    private long userId;
    private String borrowDate;
    private String dueDate ;


    public LoanCreateDTO(long bookId, long userId) {
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = LocalDate.now().toString();
        this.dueDate = LocalDate.now().plusMonths(1).toString();


    }



}
