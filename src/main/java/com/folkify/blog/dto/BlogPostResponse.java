package com.folkify.blog.dto;

import com.folkify.blog.entity.BlogPost;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlogPostResponse(
        UUID id,
        String slug,
        String title,
        String summary,
        String content,
        String coverImageUrl,
        String category,
        String authorName,
        boolean published,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static BlogPostResponse from(BlogPost p) {
        return new BlogPostResponse(
                p.getId(), p.getSlug(), p.getTitle(), p.getSummary(),
                p.getContent(), p.getCoverImageUrl(), p.getCategory(),
                p.getAuthorName(), p.isPublished(), p.getPublishedAt(),
                p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
