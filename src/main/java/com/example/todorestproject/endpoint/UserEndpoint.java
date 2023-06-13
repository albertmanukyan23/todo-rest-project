package com.example.todorestproject.endpoint;

import com.example.todorestproject.dto.CreateUserRequestDto;
import com.example.todorestproject.dto.UserAuthRequestDto;
import com.example.todorestproject.dto.UserAuthResponseDto;
import com.example.todorestproject.dto.UserDto;
import com.example.todorestproject.entity.Role;
import com.example.todorestproject.entity.User;
import com.example.todorestproject.mapper.UserMapper;
import com.example.todorestproject.security.CurrentUser;
import com.example.todorestproject.service.UserService;
import com.example.todorestproject.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserEndpoint {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil tokenUtil;
    private final UserMapper userMapper;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthRequestDto userAuthRequestDto) {
        Optional<User> byEmail = userService.findByEmail(userAuthRequestDto.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = byEmail.get();
        if (!passwordEncoder.matches(userAuthRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenUtil.generateToken(userAuthRequestDto.getEmail());
        return ResponseEntity.ok(new UserAuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody CreateUserRequestDto createUserRequestDto) {
        Optional<User> byEmail = userService.findByEmail(createUserRequestDto.getEmail());
        if (byEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User user = userMapper.map(createUserRequestDto);
        user.setPassword(passwordEncoder.encode(createUserRequestDto.getPassword()));
        user.setRole(Role.USER);
        userService.save(user);
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getAuthor(@PathVariable("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = userMapper.mapToDto(byId.get());
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable("id") int id) {
        if (userService.existsById(id)) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable("id") int id, @RequestBody User user,
                                       @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getId() == id) {
            Optional<User> byId = userService.findById(id);
            if (byId.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Optional<User> byEmail = userService.findByEmail(user.getEmail());
            if (byEmail.isPresent() && byEmail.get().getId() != id) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            User userFromDb = byId.get();
            User updatedUser = userService.update(user, userFromDb);
            UserDto userDto = userMapper.mapToDto(userService.save(updatedUser));
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
