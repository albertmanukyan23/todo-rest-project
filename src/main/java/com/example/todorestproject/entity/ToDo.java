package com.example.todorestproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "todo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
}
