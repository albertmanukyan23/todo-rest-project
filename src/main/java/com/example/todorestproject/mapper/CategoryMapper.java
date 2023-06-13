package com.example.todorestproject.mapper;

import com.example.todorestproject.dto.CategoryDto;
import com.example.todorestproject.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category map(CategoryDto dto);
    CategoryDto map(Category entity);
}
