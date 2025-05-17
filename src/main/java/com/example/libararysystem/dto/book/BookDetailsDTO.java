package com.example.libararysystem.dto.book;

public class BookDetailsDTO {
    private Long id;
    private String title;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;
    public BookDetailsDTO(Long id, String title, int publicationYear, int totalCopies, int availableCopies) {
        this.id = id;
        this.title = title;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;

    }
}
