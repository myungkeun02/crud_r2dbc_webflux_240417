package org.myungkeun.crud_r2dbc_webflux_240418.dto.base;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private List<String> error;
}
