package com.example.libararysystem.dto.book;

import com.example.libararysystem.entity.authors;
import lombok.Data;

@Data
public class BookDetailsDTO {
    private Long id;
    private String title;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;
    private authors author;
    public BookDetailsDTO(Long id, String title, int publicationYear, int totalCopies, int availableCopies , authors author) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.author = author;
    }

}
