package com.example.libararysystem.dto.book;

import com.example.libararysystem.entity.authors;
import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private int publicationYear;
    private int availableCopies;  // Added
    private int totalCopies;
    // Added

    public BookDTO(Long id, String title, int publicationYear, int availableCopies, int totalCopies) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.totalCopies = totalCopies;

    }

    public BookDTO(String title) {

        this.title = title;

    }

// Constructor, getters, setters
}