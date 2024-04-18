package org.myungkeun.crud_r2dbc_webflux_240418.services;

import org.myungkeun.crud_r2dbc_webflux_240418.dto.user.UpdateUserInfoRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> updateUserInfoByToken(String token, UpdateUserInfoRequest request);
    Mono<User> getUserInfoByToken(String token);
}
