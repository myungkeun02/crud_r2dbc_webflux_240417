package org.myungkeun.crud_r2dbc_webflux_240418.controllers;

import org.myungkeun.crud_r2dbc_webflux_240418.dto.Blog.BlogRequest;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.Blog.BlogResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.dto.base.BaseResponse;
import org.myungkeun.crud_r2dbc_webflux_240418.services.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/blog")

public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/{id}")
    Mono<ResponseEntity<BaseResponse<BlogResponse>>> getBlogById(
            @PathVariable(name = "id") Long id
    ) {
        return blogService.getBlogById(id)
                .map(blog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new BlogResponse(blog))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }

    @PostMapping
    Mono<ResponseEntity<BaseResponse<BlogResponse>>> createBlog(
            @RequestBody BlogRequest request
    ) {
        return blogService.createBlog(request)
                .map(saveBlog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("success")
                        .data(new BlogResponse(saveBlog))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }
    @GetMapping("/all")
    Flux<ResponseEntity<BaseResponse<BlogResponse>>> getAllBlog() {
        return blogService.getAllBlogs()
                .map(blog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new BlogResponse(blog))
                        .build()))
                .onErrorResume(throwable -> Flux.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<BaseResponse<String>>> deleteBlogById(
            @PathVariable(name = "id") Long id
    ) {
        return blogService.deleteBlogById(id)
                .map(message -> ResponseEntity.ok(BaseResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(message)
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<String>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(null)
                                .build()
                        )));
    }

    @PutMapping("/{id}")
    Mono<ResponseEntity<BaseResponse<BlogResponse>>> updateBlogById(
            @PathVariable(name = "id") Long id,
            @RequestBody BlogRequest request
    ) {
        return blogService.updateBlogById(id, request)
                .map(updatedBlog -> ResponseEntity.ok(BaseResponse.<BlogResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("success")
                        .data(new BlogResponse(updatedBlog))
                        .build()))
                .onErrorResume(throwable -> Mono.just(ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(BaseResponse.<BlogResponse>builder()
                                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(throwable.getMessage())
                                .data(new BlogResponse(null))
                                .build()
                        )));
    }

}

