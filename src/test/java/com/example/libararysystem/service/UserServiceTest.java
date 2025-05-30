package com.example.libararysystem.service;

import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import com.example.libararysystem.mapper.UserMapper;
import com.example.libararysystem.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;


    @Test
    void getUserByEmail() {

        //arrange
        //1.prepare data
     User user = new User();
    user.setEmail("test@test.com");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@test.com");


    //2.define mock behaviour
    when(userRepo.findByEmail("test@test.com")).thenReturn(Optional.of(user));
    when(userMapper.toDTO(user)).thenReturn(userDTO);

    //act
        UserDTO res = userService.getUserByEmail("test@test.com");
    //assert
        assertEquals("test@test.com", res.getEmail());

    //verify
    verify(userRepo).findByEmail("test@test.com");

    }
}