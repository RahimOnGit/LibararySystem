package com.example.libararysystem.mapper;

import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.dto.loan.LoanCreateDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.repository.LoanRepo;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanMapper {

    private final BookRepo bookRepo;
    private final UserRepo userRepo;
    private LoanRepo loanRepo;
    private UserDTO userDTO;
    private BookDTO bookDTO;

    public LoanMapper(BookRepo bookRepo, UserRepo userRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    public LoanDTO toDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setBorrowDate(loan.getBorrowDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnedDate(loan.getReturnedDate());
        dto.setBook(bookRepo.findById(loan.getBook().getId()).orElse(null));
        dto.setUser(userRepo.findById(loan.getUser().getId()).orElse(null));
        return dto;
    }

    public Loan toEntity(LoanCreateDTO dto) {
        Loan loan = new Loan();
        loan.setBorrowDate(dto.getBorrowDate());
        loan.setDueDate(dto.getDueDate());
      //  Optionals<>
        loan.setBook( bookRepo.findById(((long) dto.getBookId())).orElse(null));
        loan.setUser(userRepo.findById((long) dto.getUserId()).orElse(null));


        return loan;
    }
}
