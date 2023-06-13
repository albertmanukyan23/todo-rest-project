package com.example.todorestproject.dto;

import com.example.todorestproject.entity.Category;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateToDoRequestDto {
    private String title;
    @ManyToOne
    private Category category;

}
