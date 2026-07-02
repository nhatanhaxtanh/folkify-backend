package com.folkify.blog.dto;

import jakarta.validation.constraints.NotBlank;

public record BlogPostRequest(
        @NotBlank String slug,
        @NotBlank String title,
        String summary,
        String content,
        String coverImageUrl,
        String category,
        String authorName,
        boolean published
) {}
