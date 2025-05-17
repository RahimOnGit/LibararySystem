package com.example.libararysystem.dto.book;

import com.example.libararysystem.entity.authors;
import lombok.Data;

@Data
public class BookCreateDTO {

    private String title;
    private int publicationYear;
    private int availableCopies;
    private int totalCopies;
    private Long authorId;
//private authors authors;


    public BookCreateDTO(String title, int availableCopies, int publicationYear, Long authorId, int totalCopies) {
        this.title = title;
        this.availableCopies = availableCopies;
        this.publicationYear = publicationYear;
        this.authorId = authorId;
        this.totalCopies = totalCopies;
    }
}
