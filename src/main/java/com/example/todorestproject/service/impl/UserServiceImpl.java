package com.example.todorestproject.service.impl;

import com.example.todorestproject.entity.User;
import com.example.todorestproject.repository.UserRepository;
import com.example.todorestproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user, User userFromDb) {
        if (user.getName() != null && !user.getName().isEmpty()) {
            userFromDb.setName(user.getName());
        }
        if (user.getSurname() != null && !user.getSurname().isEmpty()) {
            userFromDb.setSurname(user.getSurname());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userFromDb.setEmail(user.getEmail());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            userFromDb.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userFromDb;
    }
}
