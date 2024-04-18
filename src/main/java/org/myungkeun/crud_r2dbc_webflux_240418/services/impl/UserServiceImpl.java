package org.myungkeun.crud_r2dbc_webflux_240418.services.impl;


import org.myungkeun.crud_r2dbc_webflux_240418.config.jwt.JwtTokenUtil;
import org.myungkeun.crud_r2dbc_webflux_240418.config.jwt.JwtValidationUtil;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.user.UpdateUserInfoRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;
import org.myungkeun.crud_r2dbc_webflux_240418.repositories.UserRepository;
import org.myungkeun.crud_r2dbc_webflux_240418.services.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtValidationUtil jwtValidationUtil;
    public UserServiceImpl(UserRepository userRepository, JwtValidationUtil jwtValidationUtil) {
        this.jwtValidationUtil = jwtValidationUtil;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> updateUserInfoByToken(String token, UpdateUserInfoRequest request) {
        return Mono.just(jwtValidationUtil.getEmail(token))
                .flatMap(userRepository::findByEmail)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.<User>error(new RuntimeException("error when getEmail - not found emil")))
                .flatMap(user -> {
                    if (request.getEmail() != null) {
                        user.setEmail(request.getEmail());
                    }
                    if (request.getUsername() != null) {
                        user.setUsername(request.getUsername());
                    }
                    if (request.getPhone() != null) {
                        user.setPhone(request.getPhone());
                    }
                    return userRepository.save(user);
                });
    }

    @Override
    public Mono<User> getUserInfoByToken(String token) {
        return Mono.just(jwtValidationUtil.getEmail(token))
                .flatMap(userRepository::findByEmail)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.<User>error(new RuntimeException("error when email - not found email")));
    }
}
