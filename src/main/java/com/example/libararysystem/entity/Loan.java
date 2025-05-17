package com.example.libararysystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(name = "loans")
public class Loan {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "loan_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "borrowed_date")
    private String borrowDate;
    @Column(name = "due_date")
    private String dueDate;
    @Column(name = "returned_date")
    private String returnedDate;


    //user
    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false , columnDefinition = "INTEGER")
    private User user;
    public Loan() {}

}
