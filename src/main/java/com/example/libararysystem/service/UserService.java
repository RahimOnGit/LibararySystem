package com.example.libararysystem.service;

import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.exceptions.UserValidationException;
import com.example.libararysystem.mapper.UserMapper;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Optional;

@Service
public class UserService {
private final UserRepo userRepo;
private final UserMapper userMapper;

@Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper) {
    this.userRepo = userRepo;
    this.userMapper = userMapper;
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

//get user by email
    public UserDTO getUserByEmail(String email) {
    Optional<User> user = userRepo.findByEmail(email);
        return user.map(value -> userMapper.toDTO(value)).orElse(null);
    }

}
