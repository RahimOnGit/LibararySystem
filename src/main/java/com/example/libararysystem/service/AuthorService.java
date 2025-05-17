package com.example.libararysystem.service;

import com.example.libararysystem.dto.AuthorDTO.AuthorCreateDTO;
import com.example.libararysystem.dto.AuthorDTO.AuthorDTO;
import com.example.libararysystem.dto.book.BookDTO;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.mapper.AuthorMapper;
import com.example.libararysystem.repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
AuthorRepo authorRepo;
AuthorMapper authorMapper;
@Autowired
    public AuthorService(AuthorRepo authorRepo , AuthorMapper authorMapper) {
    this.authorRepo = authorRepo;
    this.authorMapper = authorMapper;
}

//create
    public AuthorDTO create(AuthorCreateDTO dto) {

    authors author = authorMapper.toEntity(dto);

    authorRepo.save(author);

    return authorMapper.toDTO(author);

}

//author by last name
    public List<AuthorDTO> findByLastName(String lastName) {
    return authorRepo.findByLastName(lastName)
            .stream()
            .map(authorMapper::toDTO)
            .toList();

    }




public authors getAuthorById(Long id) {
    return authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));

}




public List<AuthorDTO> getAllAuthors() {
    return authorRepo.findAll()
            .stream()
            .map(authorMapper::toDTO)
            .toList();
}


public List<authors> allInfo()
{
    return authorRepo.findAll();
}






}
