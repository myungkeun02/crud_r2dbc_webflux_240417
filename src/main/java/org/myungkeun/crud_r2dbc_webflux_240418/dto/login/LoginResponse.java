package org.myungkeun.crud_r2dbc_webflux_240418.dto.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class LoginResponse {
    private String accessToken;
    private User user;
}
