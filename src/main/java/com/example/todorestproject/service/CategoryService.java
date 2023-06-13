package com.example.todorestproject.service;

import com.example.todorestproject.dto.CategoryDto;
import com.example.todorestproject.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> findById(int id);

    Category save(Category category);

    List<Category> findAll();

    boolean existsById(int id);

    void deleteById(int id);
    List<CategoryDto>fromCategoryToCategoryDtos(List<Category> all);

    Optional<Category> findByName(String name);

}
