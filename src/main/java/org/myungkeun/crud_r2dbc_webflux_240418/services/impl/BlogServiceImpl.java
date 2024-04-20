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
                .switchIfEmpty(Mono.error(new RuntimeException("blog not found")));
    }

    @Override
    public Mono<Blog> updateBlogById(Long id, BlogRequest request) {
        return blogRepository.findBlogById(id)
                .flatMap(blog -> {
                    String newTitle = request.getTitle();
                    String newDescription = request.getDescription();
                    String newCategory = request.getCategory();

                    if (newTitle != null && !newTitle.isEmpty()) {
                        blog.setTitle(newTitle);
                    }
                    if (newDescription != null && !newDescription.isEmpty()) {
                        blog.setDescription(newDescription);
                    }
                    if (newCategory != null && !newCategory.isEmpty()) {
                        blog.setCategory(newCategory);
                    }
                    return blogRepository.save(blog);
                })
                .switchIfEmpty(Mono.error(new RuntimeException("Blog not found with id: " + id)));
    }


    @Override
    public Mono<String> deleteBlogById(Long id) {
        return blogRepository.findBlogById(id)
                .flatMap(blog -> blogRepository.delete(blog).then(Mono.just("success")))
                .switchIfEmpty(Mono.error(new RuntimeException("Blog not found with id: " + id)));
    }

    @Override
    public Flux<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }
}
