package com.example.todorestproject.repository;


import com.example.todorestproject.entity.Category;
import com.example.todorestproject.entity.Status;
import com.example.todorestproject.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToDoRepository extends JpaRepository<ToDo,Integer> {
    Optional<ToDo> findByTitle(String title);

    List<ToDo> findAllByUserId(int id);

    List<ToDo> findAllByStatus(Status status);

    List<ToDo> findAllByCategory(Category category);
}
