package com.amritan.backend.mapper;

import org.springframework.stereotype.Component;

import com.amritan.backend.dto.CreateUserRequest;
import com.amritan.backend.dto.UpdateUserRequest;
import com.amritan.backend.dto.UserResponseDto;
import com.amritan.backend.entity.User;

@Component
public class UserMapper {

    private UserMapper() {}

    public static User mapToEntity(CreateUserRequest dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        return user;
    }

    public static void updateEntity(User user, UpdateUserRequest dto) {
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
    }

    public static UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getId());
        }

        return dto;
    }
}