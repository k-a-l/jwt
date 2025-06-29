package com.kalyan.springsecurity.controller;

import com.kalyan.springsecurity.model.LoginRequest;
import com.kalyan.springsecurity.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> createJwtToken(
            @RequestBody LoginRequest request
    ) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Load user details
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        // Generate token
        String token = jwtUtils.generateToken(userDetails);

        // Return token in response
        return ResponseEntity.ok(Map.of("token", token));
    }
}
