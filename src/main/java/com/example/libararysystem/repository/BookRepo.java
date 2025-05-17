package com.example.libararysystem.repository;

import com.example.libararysystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title like %:title%")
    List<Book> findByTitle(@Param("title") String title);
}
