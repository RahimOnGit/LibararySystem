package com.example.libararysystem.mapper;

import com.example.libararysystem.dto.AuthorDTO.AuthorCreateDTO;
import com.example.libararysystem.dto.AuthorDTO.AuthorDTO;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.repository.AuthorRepo;
import com.example.libararysystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    private AuthorRepo authorRepo;

    @Autowired
    public AuthorMapper(AuthorRepo repo) {
        this.authorRepo = repo;
    }
    public AuthorDTO toDTO(authors author) {
        return new AuthorDTO(
                author.getFirstName(),
                author.getLastName());

    }
    public authors toEntity(AuthorCreateDTO dto) {
        authors author = new authors();
        author.setFirstName(dto.getFirstName());
        author.setLastName(dto.getLastName());
        author.setNationality(dto.getNationality());
        author.setBirthYear(dto.getBirthYear());
        return author;
    }

}
