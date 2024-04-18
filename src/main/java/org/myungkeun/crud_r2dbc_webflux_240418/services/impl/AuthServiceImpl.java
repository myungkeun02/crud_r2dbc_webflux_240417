package org.myungkeun.crud_r2dbc_webflux_240418.services.impl;

import org.myungkeun.crud_r2dbc_webflux_240418.config.jwt.JwtTokenUtil;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.auth.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.auth.RegisterRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.Role;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;
import org.myungkeun.crud_r2dbc_webflux_240418.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_240418.services.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<User> register(RegisterRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(Objects::isNull)
                .flatMap(user -> Mono.<User>error(new RuntimeException("Email already registered")))
                .switchIfEmpty(saveNewUser(request));
    }

    @Override
    public Mono<String> loginToken(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .map(jwtTokenUtil::generateAccessToken)
                .switchIfEmpty(Mono.error(new RuntimeException("Login failed - not found email or wrong password")));
    }

    @Override
    public Mono<User> loginUser(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .switchIfEmpty(Mono.error(new RuntimeException("Login failed - not found email or wrong password")));
    }

    private Mono<User> saveNewUser(RegisterRequest request) {
        User newUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Role.ROLE_USER.name())
                .enabled(Boolean.TRUE)
                .createdBy(request.getUsername())
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(newUser);
    }
}
