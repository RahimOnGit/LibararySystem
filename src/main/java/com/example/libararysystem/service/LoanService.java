package com.example.libararysystem.service;

import com.example.libararysystem.dto.loan.LoanCreateDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.exceptions.BookNotFoundException;
import com.example.libararysystem.exceptions.LoanNotFoundException;
import com.example.libararysystem.mapper.LoanMapper;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.repository.LoanRepo;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
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


    public List<LoanDTO> getLoan(long userId) {

        return loanRepo.findByUserId(userId)
                .stream()
                .map(loanMapper::toDTO)
                .collect(Collectors.toList());

    }

    //POST
    //borrow a book
    //create loan
    @PreAuthorize("hasRole('USER')")
  @Transactional(isolation = Isolation.SERIALIZABLE , propagation = Propagation.REQUIRED)
public LoanDTO borrowBook(Long bookId, Long userId) {


        LoanCreateDTO loanCreateDTO = new LoanCreateDTO(bookId, userId);
       Loan loan =  loanMapper.toEntity(loanCreateDTO);
       if(loan.getBook()==null) throw new BookNotFoundException(bookId);
       if(loan.getBook().getAvailableCopies()<=0)
       {
           throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not borrow , no available copies");
       }

       loan.getBook().setAvailableCopies(loan.getBook().getAvailableCopies() - 1);

           loanRepo.save(loan);


           return loanMapper.toDTO(loan);

    }
    @PreAuthorize("hasRole('USER')")
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

 @PreAuthorize("hasRole('USER')")
    public LoanDTO extendDate(Long loanId) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String username = auth.getName();
         Loan loan = loanRepo.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
         if (!loan.getUser().getUsername().equals(username)) {
             throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "not allowed to extend this loan");
         }

         if (loan.getDueDate().contains("extended")) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "could not extend date because its already extended" + loan.getDueDate());
         }

         LocalDate due = LocalDate.parse(loan.getDueDate());
         loan.setDueDate(due.plusDays(14) + " extended");
         loanRepo.save(loan);
         return loanMapper.toDTO(loan);
     }
@PreAuthorize("hasRole('ADMIN')")
public List<LoanDTO> getAllLoans() {
        return loanRepo.findAll().stream().map(loanMapper::toDTO).collect(Collectors.toList());

}




}
