package org.example.controller;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RefreshTokenRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.TokenResponse;
import org.example.service.AuthService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Profile("security")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(error(exception.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (AuthenticationException | IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error("Usuário ou senha inválidos"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
        try {
            return ResponseEntity.ok(authService.refresh(request));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error(exception.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody(required = false) RefreshTokenRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }

    private record ErrorResponse(String message) { }
    private ErrorResponse error(String message) { return new ErrorResponse(message); }
}

