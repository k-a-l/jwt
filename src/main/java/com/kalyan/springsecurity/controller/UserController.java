package com.kalyan.springsecurity.controller;

import com.kalyan.springsecurity.model.LoginResponse;
import com.kalyan.springsecurity.model.User;
import com.kalyan.springsecurity.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        if (userRepository.existsUserByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(" Username already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Optional: add a default role if not set
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(List.of("ROLE_USER"));
        }

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }


    @GetMapping("/all")
    public ResponseEntity<List<LoginResponse>> getAll() {
        List<LoginResponse> responses = userRepository.findAll().stream()
                .map(user -> LoginResponse.builder()
                        .username(user.getUsername())
                        .role(user.getRole().toString())

                        .build())
                .toList();

        return ResponseEntity.ok(responses);
    }

}
