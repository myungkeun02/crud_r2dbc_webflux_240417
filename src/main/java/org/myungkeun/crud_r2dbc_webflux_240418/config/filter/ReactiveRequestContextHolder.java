package org.myungkeun.crud_r2dbc_webflux_240418.config.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

public class ReactiveRequestContextHolder {
    private static final String BEARER = "Bearer ";
    private static final String AUTHORIZATION = "authorization";

    public static Mono<String> getTokenAuth() {
        return Mono.deferContextual(Mono::just)
                .filter(contextView -> Objects.nonNull(contextView.get(ServerWebExchange.class).getRequest()))
                .map(contextView -> {
                    HttpHeaders headers = contextView.get(ServerWebExchange.class).getRequest().getHeaders();
                    return Optional.ofNullable(headers.getFirst(AUTHORIZATION))
                            .filter(authHeader -> authHeader.startsWith(BEARER))
                            .map(authHeaderBearer -> authHeaderBearer.substring(BEARER.length()))
                            .get();
                });
    }
}
