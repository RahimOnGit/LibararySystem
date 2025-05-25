package com.example.libararysystem.service;

import com.example.libararysystem.dto.loan.LoanCreateDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.exceptions.LoanNotFoundException;
import com.example.libararysystem.mapper.LoanMapper;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.repository.LoanRepo;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final BookRepo bookRepo;
    private LoanRepo loanRepo;
    private UserRepo userRepo;
    private LoanMapper loanMapper;


    @Autowired
    public LoanService(LoanRepo loanRepo, UserRepo userRepo, LoanMapper loanMapper, BookRepo bookRepo) {
        this.loanRepo = loanRepo;
        this.userRepo = userRepo;
        this.loanMapper = loanMapper;
        this.bookRepo = bookRepo;
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
    //create loan
  @Transactional(isolation = Isolation.SERIALIZABLE , propagation = Propagation.REQUIRED)
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

    public LoanDTO returnBook(Long loanId) {
        //loan
        Loan loan = loanRepo.findById(loanId).orElseThrow(()-> new LoanNotFoundException(loanId));


        if(loan.getReturnedDate()!=null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "book already returned on  " + loan.getReturnedDate());
        }


    //remove book from  loan  , book avalabilty


        //2 update book available
        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);


        //3. mark loan as returned
        loan.setReturnedDate(LocalDate.now().toString());
        Loan updatedLoan = loanRepo.save(loan);
        //CONVERT IT AS DTO
        return loanMapper.toDTO(updatedLoan);

    }


    public LoanDTO extendDate(Long loanId)
    {
      Loan loan= loanRepo.findById(loanId).orElseThrow(()-> new LoanNotFoundException(loanId));
     if(loan.getDueDate().contains("extended"))
     {
         throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not extend date because its already extended"+loan.getDueDate());
     }
      loan.setDueDate(LocalDate.now().plusDays(14) +" extended");
      loanRepo.save(loan);
      return loanMapper.toDTO(loan);
    }





}
