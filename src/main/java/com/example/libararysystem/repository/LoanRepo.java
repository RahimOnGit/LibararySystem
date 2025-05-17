package com.example.libararysystem.repository;

import com.example.libararysystem.entity.Book;
import com.example.libararysystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepo extends JpaRepository<Loan, Long> {

}
