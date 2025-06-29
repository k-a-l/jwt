package com.kalyan.springsecurity.controller;

import com.kalyan.springsecurity.model.CustomUserDetails;
import com.kalyan.springsecurity.model.LoginRequest;
import com.kalyan.springsecurity.model.User;
import com.kalyan.springsecurity.repository.UserRepository;
import com.kalyan.springsecurity.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService customUserDetailsService;
    private final JwtUtils jwtUtils;


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> createJwtToken(
            @RequestBody LoginRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/basic-success")
    public ResponseEntity<String> basicSuccess() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(auth.getName());
        String token = jwtUtils.generateToken(userDetails);
        String responseText = "Login successful!\n\nUsername: " + auth.getName() + "\nToken: " + token;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                .body(responseText);
    }

    @GetMapping("/oauth-success")
    public ResponseEntity<String> oauthSuccess(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Optional<User> existingUser = userRepository.findByUsername(email);
        User user = existingUser.orElseGet(() -> userRepository.save(
                User.builder()
                        .username(email)
                        .password("") // No password for OAuth2
                        .role(List.of("ROLE_USER"))
                        .build()
        ));

        String token = jwtUtils.generateToken(new CustomUserDetails(user));
        String responseText = "Login successful!\n\nUsername: " + user.getUsername() + "\nToken: " + token;
        return ResponseEntity.ok()
                .header("Content-Type", "text/plain")
                .body(responseText);
    }


}
