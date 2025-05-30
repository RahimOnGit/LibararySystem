package com.example.libararysystem.service;

import com.example.libararysystem.dto.AuthorDTO.AuthorDTO;
import com.example.libararysystem.entity.authors;
import com.example.libararysystem.mapper.AuthorMapper;
import com.example.libararysystem.repository.AuthorRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorServiceTest {
@ExtendWith(MockitoExtension.class)
    @Mock
    private AuthorRepo authorRepo;

private AuthorService authorService;

private final AuthorMapper authorMapper  = new AuthorMapper(authorRepo);

    @BeforeEach
    void setUp() {
        authorService = new AuthorService(authorRepo, authorMapper);

    }
    @Test
    void findByLastName() {
     authors a1 = new authors();
     a1.setFirstName("John");
     a1.setLastName("Doe");

     authors a2 = new authors();
     a2.setFirstName("Jane");
     a2.setLastName("Doe");

     when(authorRepo.findByLastName("Doe")).thenReturn(List.of(a1, a2));
     //when
        List<AuthorDTO> res = authorService.findByLastName("Doe");
        //then
        assertEquals(2, res.size());
        assertEquals("Doe", res.get(0).getLastName());
        assertEquals("Doe", res.get(1).getLastName());

        verify(authorRepo).findByLastName("Doe");

      }

      @Test
      void findByLastNameNotFound() {
        //return empty list
          //given
          when(authorRepo.findByLastName("unknown")).thenReturn(List.of());
          //when
          List<AuthorDTO> res = authorService.findByLastName("unknown");
          //then
          assertEquals(0, res.size());
          verify(authorRepo).findByLastName("unknown");
      }




    @Test
    void getAllAuthors() {
    }
}