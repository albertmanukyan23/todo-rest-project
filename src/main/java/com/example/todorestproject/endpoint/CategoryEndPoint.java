package com.example.todorestproject.endpoint;

import com.example.todorestproject.dto.CategoryDto;
import com.example.todorestproject.entity.Category;
import com.example.todorestproject.mapper.CategoryMapper;
import com.example.todorestproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryEndPoint {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto requestDto) {
        Optional<Category> byId = categoryService.findById(requestDto.getId());
        Optional<Category> byName = categoryService.findByName(requestDto.getName());
        if (byId.isEmpty() && byName.isEmpty()) {
            Category category = categoryMapper.map(requestDto);
            categoryService.save(category);
            return ResponseEntity.ok(categoryMapper.map(category));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<Category> all = categoryService.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CategoryDto> categoryDtos = categoryService.fromCategoryToCategoryDtos(all);
        return ResponseEntity.ok(categoryDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        if (categoryService.existsById(id)) {
            categoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
