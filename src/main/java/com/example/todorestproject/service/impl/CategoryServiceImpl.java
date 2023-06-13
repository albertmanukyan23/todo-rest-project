package com.example.todorestproject.service.impl;

import com.example.todorestproject.dto.CategoryDto;
import com.example.todorestproject.entity.Category;
import com.example.todorestproject.mapper.CategoryMapper;
import com.example.todorestproject.repository.CategoryRepository;
import com.example.todorestproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean existsById(int id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> fromCategoryToCategoryDtos(List<Category> all) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : all) {
            categoryDtos.add(categoryMapper.map(category));
        }
        return categoryDtos;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

}
