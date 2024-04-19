package org.myungkeun.crud_r2dbc_webflux_240418.dto.Blog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.Blog;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class BlogResponse {
    private Blog blog;
}
