package com.example.todorestproject.service;

import com.example.todorestproject.dto.ToDoDto;
import com.example.todorestproject.entity.Category;
import com.example.todorestproject.entity.Status;
import com.example.todorestproject.entity.ToDo;

import java.util.List;
import java.util.Optional;

public interface ToDoService {
    Optional<ToDo> findByTitle(String title);

    ToDo save(ToDo toDo);

    List<ToDo> findAllByUserId(int id);
    List<ToDoDto>ToDoToDtos(List<ToDo> all);

    List<ToDo> findAllByStatus(Status status);

    List<ToDo> findAllByCategory(Category category);

    Optional<ToDo> findById(int id);

    void deleteById(int id);
}
