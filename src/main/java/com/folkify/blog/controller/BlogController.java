package com.folkify.blog.controller;

import com.folkify.blog.dto.BlogPostResponse;
import com.folkify.blog.dto.BlogPostSummaryResponse;
import com.folkify.blog.repository.BlogPostRepository;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import com.folkify.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@Tag(name = "Blog", description = "API bài viết blog công khai")
public class BlogController {

    private final BlogPostRepository blogPostRepository;

    public BlogController(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @GetMapping
    @Operation(summary = "Danh sách bài viết đã xuất bản")
    public ResponseEntity<ApiResponse<List<BlogPostSummaryResponse>>> getPosts() {
        List<BlogPostSummaryResponse> posts = blogPostRepository
                .findByPublishedTrueOrderByPublishedAtDesc()
                .stream()
                .map(BlogPostSummaryResponse::from)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(posts));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Chi tiết bài viết theo slug")
    public ResponseEntity<ApiResponse<BlogPostResponse>> getPost(@PathVariable String slug) {
        BlogPostResponse post = blogPostRepository
                .findBySlugAndPublishedTrue(slug)
                .map(BlogPostResponse::from)
                .orElseThrow(() -> new ApiException(ErrorCode.BLOG_POST_NOT_FOUND));
        return ResponseEntity.ok(ApiResponse.success(post));
    }
}
