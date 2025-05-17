package com.example.libararysystem.entity;
// src/main/java/com/example/librarysystem/entity/Book.java

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

//import java.util.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Table(name = "books")  // Måste matcha tabellnamnet i databasen
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id" , columnDefinition = "INTEGER" , insertable = false)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "available_copies")
    private int availableCopies;
    @Column(name = "total_copies")
    private int totalCopies;

    //Author rel
    @ManyToOne
    @JoinColumn(name = "author_id")
    private authors authors;

    //Loan rel
    @OneToMany(mappedBy = "book" , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();

    // Add this constructor for direct creation
    public Book(String title, int publicationYear, int availableCopies, int totalCopies, authors author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;
        this.authors = author;
    }


//    public Book(String title, int publicationYear, int availableCopies, int totalCopies, Long authorId) {
//        this.title = title;
//        this.publicationYear = publicationYear;
//        this.availableCopies = availableCopies;
//        this.totalCopies = totalCopies;
//
//        if(authorId!=null) {
//            authors authors = new authors();
//            authors.setId(authorId);
//            this.authors = authors;
//        }
//        }
//
//    public Book(String title, int publicationYear, int availableCopies, int totalCopies, authors author  ) {
//        this.title = title;
//        this.publicationYear = publicationYear;
//        this.availableCopies = availableCopies;
//        this.totalCopies = totalCopies;
//        this.authors = author;
//    }


    public Book(){}

}