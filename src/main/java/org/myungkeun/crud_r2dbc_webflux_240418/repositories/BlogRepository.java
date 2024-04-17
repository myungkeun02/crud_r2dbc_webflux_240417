package org.myungkeun.crud_r2dbc_webflux_240418.repositories;

import org.myungkeun.crud_r2dbc_webflux_240418.entities.Blog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BlogRepository extends ReactiveCrudRepository<Blog, Long> {
    Mono<Blog> findBlogById(Long id);
    Mono<Blog> findBlogByTitle(String title);
}
