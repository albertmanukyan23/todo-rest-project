package com.example.todorestproject.mapper;

import com.example.todorestproject.dto.CreateToDoRequestDto;
import com.example.todorestproject.dto.ToDoDto;
import com.example.todorestproject.entity.ToDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
    ToDo map(CreateToDoRequestDto dto);
    ToDoDto map(ToDo entity);
}
