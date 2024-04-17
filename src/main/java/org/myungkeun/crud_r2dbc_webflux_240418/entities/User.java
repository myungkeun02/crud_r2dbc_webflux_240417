package org.myungkeun.crud_r2dbc_webflux_240418.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table("public.user")

public class User extends Base {
    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("phone")
    private String phone;

    @Column("password")
    private String password;

    @Column("enabled")
    private Boolean enabled;

    @Column("roles")
    private String roles;
}
