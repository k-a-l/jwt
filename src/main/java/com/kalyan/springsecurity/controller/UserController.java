package com.kalyan.springsecurity.controller;

import com.kalyan.springsecurity.model.User;
import com.kalyan.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> signp(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/open")
    public String open() {
        return "This is open";
    }

}
