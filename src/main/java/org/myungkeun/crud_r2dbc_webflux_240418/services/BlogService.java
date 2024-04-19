package org.myungkeun.crud_r2dbc_webflux_240418.services;

import org.myungkeun.crud_r2dbc_webflux_240418.dto.Blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.Blog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BlogService {
    Mono<Blog> createBlog(BlogRequest request);
    Mono<Blog> getBlogById(Long id);
    Mono<Blog> updateBlogById(Long id, BlogRequest request);
    Mono<String> deleteBlogById(Long id);
    Flux<Blog> getAllBlogs();
}
