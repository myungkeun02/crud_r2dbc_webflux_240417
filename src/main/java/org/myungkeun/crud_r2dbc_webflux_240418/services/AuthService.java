package org.myungkeun.crud_r2dbc_webflux_240418.services;

import org.myungkeun.crud_r2dbc_webflux_240418.dto.login.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.login.LoginResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.register.RegisterRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<User> register(RegisterRequest request);

    Mono<String> loginToken(LoginRequest request);

    Mono<User> loginUser(LoginRequest request);
}
