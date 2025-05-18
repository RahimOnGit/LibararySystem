package com.example.libararysystem.service;

import com.example.libararysystem.dto.loan.LoanCreateDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.mapper.LoanMapper;
import com.example.libararysystem.repository.LoanRepo;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private LoanRepo loanRepo;
    private UserRepo userRepo;
    private LoanMapper loanMapper;


    @Autowired
    public LoanService(LoanRepo loanRepo, UserRepo userRepo,LoanMapper loanMapper) {
        this.loanRepo = loanRepo;
        this.userRepo = userRepo;
        this.loanMapper = loanMapper;
    }
    //all loans
    public List<LoanDTO> getLoans() {

      return loanRepo.findAll().stream()
              .map(loanMapper::toDTO)
              .collect(Collectors.toList());
    }

//GET /users/{userId}/loans - Hämta användarens lån
    //find the user by id
    public List<LoanDTO> getLoanByUserId(int userId) {

        return loanRepo.findByUserId(userId)
                .stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());

    }

    //POST
    //borrow a book
public LoanDTO borrowBook(int bookId, int userId) {
        LoanCreateDTO loanCreateDTO = new LoanCreateDTO(bookId, userId);
       Loan loan =  loanMapper.toEntity(loanCreateDTO);


       if(loan.getBook().getAvailableCopies()<=0)
       {
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not borrow copies");
       }

       loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() - 1);

           loanRepo.save(loan);
           return loanMapper.toDTO(loan);

    }



}
