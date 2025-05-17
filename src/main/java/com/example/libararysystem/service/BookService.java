package com.example.libararysystem.service;

import com.example.libararysystem.dto.book.BookCreateDTO;
import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.mapper.BookMapper;
import com.example.libararysystem.repository.BookRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepo bookRepo;
    private final AuthorService authorService;
    private final BookMapper bookMapper;
    @Autowired
    public BookService(BookRepo bookRepo , AuthorService authorService, BookMapper bookMapper ) {
        this.bookRepo = bookRepo;
        this.authorService = authorService;
        this.bookMapper = bookMapper;

    }
    // - Lista alla böcker

    public List<BookDTO> findAll() {
        return bookRepo.findAll()
                .stream().map(bookMapper::toDto).toList();

    }




//GET /books/search - Sök böcker på title(query parameters)
public List<BookDTO> findByTitle(String title) {
       return bookRepo.findByTitle(title)
               .stream()
               .map(bookMapper::toDto)
               .toList();
    }

    //POST /books - Skapa ny bok
    public BookDTO create(BookCreateDTO dto) {

        if(dto!=null)
        {
            Book book = bookMapper.toEntity(dto);
            bookRepo.save(book);
            return bookMapper.toDto(book);
        }
       throw new IllegalArgumentException("BookCreateDTO cannot be null");
         }
//@Transactional
//    public Book save(Book book)
//    {
//        return bookRepo.save(book);
//    }

}
