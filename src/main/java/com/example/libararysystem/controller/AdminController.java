package com.example.libararysystem.controller;

import com.example.libararysystem.entity.User;
import com.example.libararysystem.repository.UserRepo;
import com.example.libararysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final UserService userService;
    private final UserRepo userRepo;
    @Autowired
    public AdminController(UserService userService , UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping("/users/allInfo")
    public ResponseEntity<List<User>> getAllInfo() {

        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("/users/delete")
    public ResponseEntity<?> deleteUser(@RequestParam("userId") long id)
    {
       Optional<User> user =  userService.deleteUserById(id);
        return user.map(value ->
                ResponseEntity.ok().body("successfully deleted\nuser id :" + value.getId()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id :" + id));
       }

       @PutMapping("/users/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam("userId") long id, @RequestParam("status") boolean status)
    {
        User user = userService.changeStatusUserById(id,status);
        return ResponseEntity.ok().body(user);

    }



}
