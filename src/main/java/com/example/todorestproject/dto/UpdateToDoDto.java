package com.example.todorestproject.dto;

import com.example.todorestproject.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateToDoDto {
    private Status status;
}
