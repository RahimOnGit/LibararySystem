package com.example.libararysystem.service;

import com.example.libararysystem.dto.loan.LoanCreateDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.mapper.LoanMapper;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.repository.LoanRepo;
import com.example.libararysystem.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private BookRepo bookRepo;

    @Mock
    private LoanRepo loanRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    @Test
    void borrowBook_returnsLoanDTO_WhenSuccessful() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAvailableCopies(5);

        User user = new User();
        user.setId(1L);

        Loan loan = new Loan();
        loan.setId(1);
        loan.setUser(user);
        loan.setBook(book);

        LoanDTO mockDto = new LoanDTO();
        mockDto.setBook(book);
        mockDto.setUser(user);

        when(loanRepo.save(loan)).thenReturn(loan);
        when(loanMapper.toEntity(new LoanCreateDTO(loan.getBook().getId(),loan.getUser().getId()))).thenReturn(loan);
       when(loanMapper.toDTO(loan)).thenReturn(mockDto);

        // Act
        LoanDTO result = loanService.borrowBook(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("Clean Code", result.getBook().getTitle());


        verify(loanRepo).save(loan);
        verify(loanMapper).toDTO(loan);



    }

//Skriv ett test som kontrollerar att man inte kan lägga ett lån om boken har 0 available copies


    @Test
    void borrowBook_Fails_WhenNoCopiesLeft() {
       //a
        Book book = new Book();
        book.setId(1L);
        book.setAvailableCopies(0);

        User user = new User();
        user.setId(1L);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);

        when(loanMapper.toEntity(new LoanCreateDTO(loan.getBook().getId(),loan.getUser().getId()))).thenReturn(loan);
       //a
        assertThrows(
                ResponseStatusException.class,
                () -> loanService.borrowBook(1L, 1L)
        );
//a
        verify(loanRepo, never()).save(loan);
    }

}


