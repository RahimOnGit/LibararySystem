package com.example.libararysystem.repository;

import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email ")
    Optional<User> findByEmail(@Param("email") String email);

}
