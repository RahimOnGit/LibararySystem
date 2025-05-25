package com.example.libararysystem.controller;

import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import com.example.libararysystem.service.LoanService;
import com.example.libararysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepo userRepo;
    private final UserService userService;
    private final LoanService loanService;
    @Autowired
    public UserController(UserService userService, UserRepo userRepo , LoanService loanService) {
        this.userRepo = userRepo;
        this.userService = userService;
        this.loanService = loanService;
    }
    //post
    @PostMapping("/loans")
    public ResponseEntity<?> createLoan(@RequestParam("bookId") int bookId , @RequestParam("userId") int userId) {

        LoanDTO loanDTO = loanService.borrowBook(bookId,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanDTO);

    }

    @PutMapping("/loans/{id}/extend")
    public ResponseEntity<?> extendLoan(@PathVariable("id") long loanId)
    {

        LoanDTO loanDTO = loanService.extendDate(loanId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(loanDTO);
    }


    @PutMapping("/loans/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable long id) {
        LoanDTO dto = loanService.returnBook(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);

    }

    //loan by user id
    @GetMapping("{id}/loans")
    public ResponseEntity<List<LoanDTO>> getLoans(@PathVariable int id) {
      List<LoanDTO>  dto  = loanService.getLoanByUserId(id);
        return ResponseEntity.ok(dto);
    }




//create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO user) {
        UserDTO dto = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
       UserDTO userDTO =  userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/allInfo")
    public ResponseEntity<List<User>> getAllInfo() {

        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }

}
