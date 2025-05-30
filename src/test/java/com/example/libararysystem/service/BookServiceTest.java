package com.example.libararysystem.service;

import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.mapper.BookMapper;
import com.example.libararysystem.repository.BookRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepo bookRepo;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;


    @Test
    void findByTitle_exists_returnsBook() {

        //given
        Book book = new Book();
        book.setTitle("Clean Code");

        BookDTO bookDTO = new BookDTO("Clean Code");


        //mock repo and mapper
        when(bookRepo.findByTitle("Clean Code")).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDTO);
        //when

       List <BookDTO> result = bookService.findByTitle("Clean Code");

       //assert
        assertEquals(1,result.size());
        assertEquals("Clean Code",result.get(0).getTitle());

        verify(bookRepo).findByTitle("Clean Code");
        verify(bookMapper).toDto(book);

    }

    @Test
    void create() {
    }
}