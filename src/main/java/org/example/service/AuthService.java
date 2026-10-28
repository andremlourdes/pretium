package org.example.service;

import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RefreshTokenRequest;
import org.example.dto.auth.RegisterRequest;
import org.example.dto.auth.TokenResponse;
import org.example.dto.auth.UserResponse;
import org.example.entity.AppUser;
import org.example.entity.RefreshToken;
import org.example.repository.AppUserRepository;
import org.example.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Profile("security")
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AppUserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final long refreshTokenDays;

    public AuthService(AppUserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       @Value("${pretium.security.jwt.refresh-token-days:7}") long refreshTokenDays) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenDays = refreshTokenDays;
    }

    @Transactional
    public TokenResponse register(RegisterRequest request) {
        validateRegistration(request);
        if (userRepository.existsByUsernameIgnoreCase(request.username().trim())) {
            throw new IllegalArgumentException("Nome de usuário já cadastrado");
        }
        if (userRepository.existsByEmailIgnoreCase(request.email().trim())) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        AppUser user = new AppUser();
        user.setUsername(request.username().trim());
        user.setEmail(request.email().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setEnabled(true);
        return issueTokens(userRepository.save(user));
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        return issueTokens((AppUser) authentication.getPrincipal());
    }

    @Transactional
    public TokenResponse refresh(RefreshTokenRequest request) {
        if (request == null || request.refreshToken() == null || request.refreshToken().isBlank()) {
            throw new IllegalArgumentException("Refresh token obrigatório");
        }
        RefreshToken stored = refreshTokenRepository.findByToken(request.refreshToken())
            .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido"));
        if (stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Refresh token expirado ou revogado");
        }
        stored.setRevoked(true);
        refreshTokenRepository.save(stored);
        return issueTokens(stored.getUser());
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {
        if (request == null || request.refreshToken() == null) return;
        refreshTokenRepository.findByToken(request.refreshToken()).ifPresent(token -> {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        });
    }

    private TokenResponse issueTokens(AppUser user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshTokenValue = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(refreshTokenValue, user,
            LocalDateTime.now().plusDays(refreshTokenDays));
        refreshTokenRepository.save(refreshToken);
        return new TokenResponse(accessToken, refreshTokenValue, "Bearer",
            jwtService.getAccessTokenSeconds(), UserResponse.from(user));
    }

    private void validateRegistration(RegisterRequest request) {
        if (request == null || request.username() == null || request.username().trim().length() < 3) {
            throw new IllegalArgumentException("Usuário deve ter pelo menos 3 caracteres");
        }
        if (request.email() == null || !request.email().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("E-mail inválido");
        }
        if (request.password() == null || request.password().length() < 8) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 8 caracteres");
        }
    }
}
