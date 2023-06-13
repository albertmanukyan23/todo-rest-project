package com.example.todorestproject.mapper;


import com.example.todorestproject.dto.CreateUserRequestDto;
import com.example.todorestproject.dto.UserDto;
import com.example.todorestproject.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(CreateUserRequestDto dto);

    UserDto mapToDto(User entity);
}
