package com.example.libararysystem.mapper;

import com.example.libararysystem.dto.book.BookCreateDTO;
import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.dto.book.BookDetailsDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.repository.AuthorRepo;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;
import jakarta.persistence.*;

@Component
public class BookMapper {

    private  final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @Autowired
    public BookMapper(AuthorRepo authorRepo, BookRepo bookRepo, AuthorService authorService, AuthorMapper authorMapper) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    public  BookDTO toDto(Book book) {
        if (book == null) return null;

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies());
//                book.getAuthors());
//        != null ? book.getAuthors().getId() : null  // Include author ID

    }

    public Book toEntity(BookCreateDTO dto) {
        if (dto == null) return null;

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        book.setAuthors(authorService.getAuthorById(dto.getAuthorId()));

        return book;
    }


    public  BookDetailsDTO toDtoDetails(Book book) {
        if (book == null) return null;

        return new BookDetailsDTO(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                book.getTotalCopies(),
                authorMapper.toDTO(book.getAuthors()));
//                book.getAuthors());
//        != null ? book.getAuthors().getId() : null  // Include author ID

    }


}