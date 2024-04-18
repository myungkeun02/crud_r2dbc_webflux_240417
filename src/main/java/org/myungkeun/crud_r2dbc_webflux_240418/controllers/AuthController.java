package org.myungkeun.crud_r2dbc_webflux_240418.controllers;

import lombok.AllArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.base.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.login.LoginRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.login.LoginResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.register.RegisterRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.register.RegisterResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authSErvice) {
        this.authService = authSErvice;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<BaseResponse<RegisterResponse>>> register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request)
                .map(user -> ResponseEntity.ok(BaseResponse.<RegisterResponse>builder()
                        .statusCode(201)
                        .message("success")
                        .data(new RegisterResponse(user))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<RegisterResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new RegisterResponse(null))
                                .build()
                        )));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<BaseResponse<LoginResponse>>> login(
            @RequestBody LoginRequest request
    ) {
        return authService.loginToken(request)
                .flatMap(token -> authService.loginUser(request)
                        .map(user -> ResponseEntity.ok(BaseResponse.<LoginResponse>builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("success")
                                .data(new LoginResponse(token, user))
                                .build()))
                        .switchIfEmpty(Mono.just(ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(BaseResponse.<LoginResponse>builder()
                                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .message("Login failed - not found email or wrong password")
                                        .data(null)
                                        .build()
                                )))
                )
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<LoginResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build()
                        )));
    }
}
