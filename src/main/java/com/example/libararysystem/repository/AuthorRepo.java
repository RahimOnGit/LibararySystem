package com.example.libararysystem.repository;

import com.example.libararysystem.dto.AuthorDTO.AuthorDTO;
import com.example.libararysystem.entity.authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepo extends JpaRepository<authors, Long> {
    @Query("select a from authors a where a.lastName like %:lastName%")
    List<authors> findByLastName(@Param("lastName") String lastName);
}
