package org.myungkeun.crud_r2dbc_webflux_240418.services.impl;

import org.myungkeun.crud_r2dbc_webflux_240418.dto.Blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.entities.Blog;
import org.myungkeun.crud_r2dbc_webflux_240418.repositories.BlogRepository;
import org.myungkeun.crud_r2dbc_webflux_240418.services.BlogService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Mono<Blog> createBlog(BlogRequest request) {
        Blog newBlog = new Blog(request.getTitle(), request.getDescription(), request.getCategory());
        return blogRepository.save(newBlog);
    }

    @Override
    public Mono<Blog> getBlogById(Long id) {
        return blogRepository.findBlogById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("blog not found")))
    }

    @Override
    public Mono<Blog> updateBlogById(Long id, BlogRequest request) {
        Blog oldBlog = blogRepository.findBlogById(id).block();
        return null;
    }

    @Override
    public Mono<String> deleteBlogById(Long id) {
        Blog oldBlog = blogRepository.findBlogById(id)
                .map(blog -> {
                    blogRepository.delete(blog);
                    return "success";
                })
                .switchIfEmpty(Mono.<String>error(new RuntimeException("blog not found")));
    }

    @Override
    public Flux<Blog> getAllBlogs() {
        return null;
    }
}
