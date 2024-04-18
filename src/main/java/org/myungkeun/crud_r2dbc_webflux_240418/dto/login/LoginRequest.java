package org.myungkeun.crud_r2dbc_webflux_240418.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class LoginRequest {
    private String email;
    private String password;
}
