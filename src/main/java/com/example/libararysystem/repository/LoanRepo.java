package com.example.libararysystem.repository;

import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {

    @Query("select l from Loan l where l.user.id = :userId")
    public List<Loan> findByUserId(@Param("userId") int userId);
}
