package com.example.libararysystem.service;

import com.example.libararysystem.dto.book.BookCreateDTO;
import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.dto.book.BookDetailsDTO;
import com.example.libararysystem.entity.Book;
import com.example.libararysystem.exceptions.BookNotFoundException;
import com.example.libararysystem.mapper.BookMapper;
import com.example.libararysystem.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<BookDetailsDTO> findAll() {
        return bookRepo.findAll()
                .stream().map(bookMapper::toDtoDetails).toList();

    }

    public Page<BookDetailsDTO> findAll(Pageable pageable) {
        Page<Book> books = bookRepo.findAll(pageable);
        List<BookDetailsDTO> dto = books.getContent().stream().map(bookMapper::toDtoDetails).toList();

        return books.map(bookMapper::toDtoDetails);

    }




//GET /books/search - Sök böcker på title(query parameters)
public List<BookDTO> findByTitle(String title)  {

       List<Book> books = bookRepo.findByTitle(title);
               if(books.isEmpty()) {
                throw new BookNotFoundException(title);
               }
               return books.stream()
                       .map(bookMapper::toDto)
                       .collect(Collectors.toList());


    }

    //POST /books - Skapa ny bok
    public BookDTO create(BookCreateDTO dto) {

        if(dto!=null)
        {
            Book book = bookMapper.toEntity(dto);
            bookRepo.save(book);
            return bookMapper.toDto(book);
        }
       throw new IllegalArgumentException("book cannot be null");
         }
//@Transactional
//    public Book save(Book book)
//    {
//        return bookRepo.save(book);
//    }

}
