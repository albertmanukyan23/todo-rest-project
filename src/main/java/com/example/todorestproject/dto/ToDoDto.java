package com.example.todorestproject.dto;

import com.example.todorestproject.entity.Category;
import com.example.todorestproject.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDto {
    private int id;
    private String title;
    private Status status;
    private Category category;
}
