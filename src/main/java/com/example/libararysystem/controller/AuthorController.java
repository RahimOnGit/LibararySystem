package com.example.libararysystem.controller;

import com.example.libararysystem.dto.AuthorDTO.AuthorCreateDTO;
import com.example.libararysystem.dto.AuthorDTO.AuthorDTO;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.repository.AuthorRepo;
import com.example.libararysystem.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //create
    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorCreateDTO dto) {
        AuthorDTO author = authorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

//all authors
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll() {
        List<AuthorDTO> dto = authorService.getAllAuthors();
        return ResponseEntity.ok(dto);
    }

    //by last name
    @GetMapping("/search/name/{lastName}")
    public ResponseEntity<List<AuthorDTO>> findByLastName(@PathVariable String lastName) {
        List<AuthorDTO> dtos = authorService.findByLastName(lastName);
        return ResponseEntity.ok(dtos);
    }

    //all info
    @GetMapping("/allinfo")
    public ResponseEntity<List<authors>> allInfo() {
        List<authors> authors =  authorService.allInfo();
        return ResponseEntity.ok(authors);
    }


}
