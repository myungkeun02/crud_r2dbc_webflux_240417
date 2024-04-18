package org.myungkeun.crud_r2dbc_webflux_240418.controllers;

import org.myungkeun.crud_r2dbc_webflux_240418.config.filter.ReactiveRequestContextHolder;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.base.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.user.UpdateUserInfoRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.user.UserInfoResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;
import org.myungkeun.crud_r2dbc_webflux_240418.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")

public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Mono<ResponseEntity<BaseResponse<UserInfoResponse>>> getUserInfoByToken() {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(userService::getUserInfoByToken)
                .map(user -> ResponseEntity.ok(BaseResponse.<UserInfoResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("get user info success")
                        .data(new UserInfoResponse(user))
                        .build()))
                .onErrorResume(throwble -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<UserInfoResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwble.getMessage())
                                .data(null)
                                .build())));
    }

    @PutMapping
    public Mono<ResponseEntity<BaseResponse<UserInfoResponse>>> updateUserInfoByToken(
            @RequestBody UpdateUserInfoRequest request
    ) {
        return ReactiveRequestContextHolder.getTokenAuth()
                .flatMap(token -> userService.updateUserInfoByToken(token, request))
                .map(user -> ResponseEntity.ok(BaseResponse.<UserInfoResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("update user success")
                        .data(new UserInfoResponse(user))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<UserInfoResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build())));
    }
}
