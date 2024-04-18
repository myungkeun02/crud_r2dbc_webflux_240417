package org.myungkeun.crud_r2dbc_webflux_240418.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RegisterRequest {
    private String username;
    private String email;
    private String phone;
    private String password;
}
