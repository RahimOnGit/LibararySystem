package com.example.libararysystem.service;

import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.exceptions.UserValidationException;
import com.example.libararysystem.mapper.UserMapper;
import com.example.libararysystem.repository.UserRepo;
import com.example.libararysystem.utilities.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;


@Service
public class UserService {

private final UserRepo userRepo;
private final UserMapper userMapper;
private final PasswordEncoder passwordEncoder;
private final Validator validator;


@Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper, PasswordEncoder passwordEncoder, Validator validator) {
    this.userRepo = userRepo;
    this.userMapper = userMapper;
    this.passwordEncoder = passwordEncoder;
    this.validator = validator;

}
//new user
public UserDTO createUser(UserCreateDTO dto) {

        if(dto.getFirstName() == null || dto.getFirstName().isEmpty()) throw new UserValidationException("First name cannot be empty");
        if(dto.getLastName() == null || dto.getLastName().isEmpty()) throw new UserValidationException("Last name cannot be empty");
        if(dto.getEmail() == null || dto.getEmail().isEmpty()) throw new UserValidationException("Email cannot be empty");

    if(dto.getPassword() == null || dto.getPassword().isEmpty()) throw new UserValidationException("Password cannot be empty");
    if(dto.getPassword().length() < 8) throw new UserValidationException("Password must be at least 8 characters");





    User user = userMapper.toEntity(dto);
    user.setPassword( dto.getPassword());

    userRepo.save(user);
    return userMapper.toDTO(user);
    }


    public void register(UserCreateDTO dto) {

    if(userRepo.findByEmail(dto.getEmail()).isPresent()) {
        throw new UserValidationException("Email already registered");
    }
    if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
        throw new UserValidationException("Username already taken");
    }

        List<String> errors  = validator.validatePassword(dto.getPassword());
        if(!errors.isEmpty())
            throw new UserValidationException("validation failed: "+errors);


        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // IMPORTANT
        user.setEnabled(true);
        String role = dto.getRole();
        if(role== null || role.trim().isEmpty())
        {
            role = "ROLE_USER";
        }
        user.setRole(role.toUpperCase());
     userRepo.save(user);
        }


        public void registerAdmin(UserCreateDTO dto) {

    dto.setRole("ROLE_ADMIN");
    register(dto);

        }

        @PreAuthorize("hasRole('ADMIN')")
//get user by email
public UserDTO getUserByEmail(String email) {
    Optional<User> user = userRepo.findByEmail(email);
        return user.map(value -> userMapper.toDTO(value)).orElse(null);
    }


    //delete user
    @PreAuthorize("hasRole('ADMIN')")

    public Optional<User> deleteUserById(Long id) {
    Optional<User> user = userRepo.findById(id);
    if(user.isPresent()) {
        userRepo.delete(user.get());
    }
    return user;
    }

    //deactivate user
@PreAuthorize("hasRole('ADMIN')")
    public User changeStatusUserById(Long id, boolean status) {
    Optional<User> user = userRepo.findById(id);

    if(user.isPresent()) {
        user.get().setEnabled(status);
        userRepo.save(user.get());
    }
    else
        throw new UserValidationException("User not found with id :" + id);

    return user.get();
    }



    public Optional<User> getUserByUsername(String username) {

        Optional<User> user =  userRepo.findByUsername(username);
        if(user.isPresent()) {
            return user;
        }
        else
            throw new UserValidationException("User not found with name :" + username);
}


}
