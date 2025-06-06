package com.example.libararysystem.controller;
import com.example.libararysystem.dto.book.BookCreateDTO;
import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.dto.book.BookDetailsDTO;
import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.service.AuthorService;
import com.example.libararysystem.service.BookService;
import com.example.libararysystem.service.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.libararysystem.repository.BookRepo;
import com.example.libararysystem.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepo bookRepo;


    private final BookService bookService;
    @Autowired
    private AuthorService authorService;
    private LoanService loanService;

    public BookController(BookService bookService,LoanService loanService ,  AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.loanService = loanService;

    }

@GetMapping("/paged")
public ResponseEntity<Page<BookDetailsDTO>> findAll
        (@RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "5") int size,
         @RequestParam(defaultValue = "id") String sortBy,
         @RequestParam(defaultValue = "asc") String order) {

    Sort sort = order.equalsIgnoreCase("desc") ?
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page,size,sort);
    Page<BookDetailsDTO> pagedBooks = bookService.findAll(pageable);
    return ResponseEntity.ok((pagedBooks));


}


@GetMapping("/search")
    // get book by title
    public ResponseEntity<List<BookDTO>> findByTitle(@RequestParam String title) {

    List<BookDTO> dto = bookService.findByTitle(title);
    return ResponseEntity.ok(dto);

    }

    //create new book

    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookCreateDTO book) {
        BookDTO createdBook = bookService.create(book);
        return ResponseEntity.ok(createdBook);
    }



//    @PostMapping
//    public ResponseEntity<Book> createRawBook(@RequestBody Book book) {
//
//        if (book.getAuthors() != null && book.getAuthors().getId() != null) {
//            authors author = authorService.getAuthorById(book.getAuthors().getId());
//            book.setAuthors(author);
//        }
//
//        Book savedBook = bookRepo.save(book);
//        return ResponseEntity.ok(savedBook);
//    }












    @GetMapping("/test")
    public ResponseEntity<List<Book>> test() {
List<Book> books = bookRepo.findAll();
        return ResponseEntity.ok(books);

    }


    @GetMapping
    public ResponseEntity<List<BookDetailsDTO>> findAll() {
        List<BookDetailsDTO> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }





}

