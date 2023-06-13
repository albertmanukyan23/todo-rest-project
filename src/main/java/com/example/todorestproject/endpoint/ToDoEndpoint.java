package com.example.todorestproject.endpoint;

import com.example.todorestproject.dto.CreateToDoRequestDto;
import com.example.todorestproject.dto.ToDoDto;
import com.example.todorestproject.dto.UpdateToDoDto;
import com.example.todorestproject.entity.Category;
import com.example.todorestproject.entity.Status;
import com.example.todorestproject.entity.ToDo;
import com.example.todorestproject.mapper.ToDoMapper;
import com.example.todorestproject.security.CurrentUser;
import com.example.todorestproject.service.CategoryService;
import com.example.todorestproject.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class ToDoEndpoint {
    private final ToDoMapper toDoMapper;
    private final ToDoService toDoService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ToDoDto> create(@RequestBody CreateToDoRequestDto requestDto,
                                          @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<ToDo> byTitle = toDoService.findByTitle(requestDto.getTitle());
        Category category = requestDto.getCategory();
        if (byTitle.isPresent() || categoryService.existsById(category.getId()) ||
                categoryService.findByName(category.getName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        ToDo toDo = toDoMapper.map(requestDto);
        toDo.setUser(currentUser.getUser());
        toDo.setStatus(Status.NOT_STARTED);
        categoryService.save(requestDto.getCategory());
        toDoService.save(toDo);
        return ResponseEntity.ok(toDoMapper.map(toDo));
    }

    @GetMapping
    public ResponseEntity<List<ToDoDto>> getToDos(@AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser() != null) {
            List<ToDo> toDoList = toDoService.findAllByUserId(currentUser.getUser().getId());
            List<ToDoDto> toDoDtos = toDoService.ToDoToDtos(toDoList);
            return ResponseEntity.ok(toDoDtos);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<ToDoDto>> getByStatus(@RequestParam("status") Status status,
                                                     @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser() != null) {
            List<ToDo> toDoList = toDoService.findAllByStatus(status);
            List<ToDoDto> toDoDtos = toDoService.ToDoToDtos(toDoList);
            return ResponseEntity.ok(toDoDtos);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/byCategory")
    public ResponseEntity<List<ToDoDto>> getByCategory(@RequestParam("id") int id,
                                                       @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser() != null && categoryService.existsById(id)) {
            Optional<Category> byId = categoryService.findById(id);
            List<ToDo> toDoList = toDoService.findAllByCategory(byId.get());
            List<ToDoDto> toDoDtos = toDoService.ToDoToDtos(toDoList);
            return ResponseEntity.ok(toDoDtos);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<ToDoDto> updateStatus(@RequestParam("id") int id, @RequestBody UpdateToDoDto updateToDoDto,
                                                @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<ToDo> byId = toDoService.findById(id);
        if (currentUser.getUser() != null && byId.isPresent()) {
            ToDo toDo = byId.get();
            toDo.setStatus(updateToDoDto.getStatus());
            return ResponseEntity.ok(toDoMapper.map(toDoService.save(toDo)));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser != null) {
            Optional<ToDo> byId = toDoService.findById(id);
            ToDo toDo = byId.get();
            if (toDo.getUser().getId() == currentUser.getUser().getId()) {
                toDoService.deleteById(id);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

}
