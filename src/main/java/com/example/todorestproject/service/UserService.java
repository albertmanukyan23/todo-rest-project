package com.example.todorestproject.service;

import com.example.todorestproject.dto.CategoryDto;
import com.example.todorestproject.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(int id);

    boolean existsById(int id);

    void deleteById(int id);
    User update(User user,User userFromDb);

}
