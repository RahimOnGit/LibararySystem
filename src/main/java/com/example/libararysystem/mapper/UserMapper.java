package com.example.libararysystem.mapper;

import com.example.libararysystem.dto.user.UserCreateDTO;
import com.example.libararysystem.dto.user.UserDTO;
import com.example.libararysystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setRegistration(dto.getRegistration());
        return user;

    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail());
    }

}
