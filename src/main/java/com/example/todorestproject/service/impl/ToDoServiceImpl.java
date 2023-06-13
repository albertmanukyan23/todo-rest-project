package com.example.todorestproject.service.impl;

import com.example.todorestproject.dto.ToDoDto;
import com.example.todorestproject.entity.Category;
import com.example.todorestproject.entity.Status;
import com.example.todorestproject.entity.ToDo;
import com.example.todorestproject.mapper.ToDoMapper;
import com.example.todorestproject.repository.ToDoRepository;
import com.example.todorestproject.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {
    private final ToDoMapper toDoMapper;
    private  final ToDoRepository toDoRepository;

    @Override
    public Optional<ToDo> findByTitle(String title) {
        return toDoRepository.findByTitle(title);
    }

    @Override
    public ToDo save(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    @Override
    public List<ToDo> findAllByUserId(int id) {
        return toDoRepository.findAllByUserId(id);
    }

    @Override
    public List<ToDoDto> ToDoToDtos(List<ToDo> all) {
        List<ToDoDto> toDoDtos = new ArrayList<>();
        for (ToDo toDo : all) {
            toDoDtos.add(toDoMapper.map(toDo));
        }
        return toDoDtos;
    }

    @Override
    public List<ToDo> findAllByStatus(Status status) {
        return toDoRepository.findAllByStatus(status);
    }

    @Override
    public List<ToDo> findAllByCategory(Category category) {
        return toDoRepository.findAllByCategory(category);
    }

    @Override
    public Optional<ToDo> findById(int id) {
        return toDoRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        toDoRepository.deleteById(id);
    }
}
