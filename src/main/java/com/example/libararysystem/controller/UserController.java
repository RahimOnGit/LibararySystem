package com.example.libararysystem.controller;

import com.example.libararysystem.dto.loan.LoanDTO;
import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import com.example.libararysystem.service.LoanService;
import com.example.libararysystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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



    //create user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        UserDTO dto = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO =  userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
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
    @GetMapping("/loans")
    public ResponseEntity<List<LoanDTO>> getLoans() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
     String username = auth.getName();
     Optional<User> user = userService.getUserByUsername(username);
      List<LoanDTO>  dto  = loanService.getLoan(user.get().getId());

        return ResponseEntity.ok(dto);
    }

    //post
    @PostMapping("/loans")
    public ResponseEntity<?> createLoan(@RequestParam("bookId") long bookId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> user = userService.getUserByUsername(username);
if(user.get().isEnabled()) {
    LoanDTO loanDTO = loanService.borrowBook(bookId, user.get().getId());

    return ResponseEntity.status(HttpStatus.CREATED).body(loanDTO);
}
else {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("account is suspended" + username);
}
    }



}
