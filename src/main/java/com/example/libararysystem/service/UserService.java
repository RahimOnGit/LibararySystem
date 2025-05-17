package com.example.libararysystem.service;

import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.mapper.UserMapper;
import com.example.libararysystem.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Optional;

@Service
public class UserService {
private UserRepo userRepo;
private UserMapper userMapper;

@Autowired
    public UserService(UserRepo userRepo, UserMapper userMapper) {
    this.userRepo = userRepo;
    this.userMapper = userMapper;
}
//new user
    public UserDTO createUser(UserCreateDTO dto) {

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
