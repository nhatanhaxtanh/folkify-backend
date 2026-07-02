package com.folkify.blog.dto;

import com.folkify.blog.entity.BlogPost;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlogPostSummaryResponse(
        UUID id,
        String slug,
        String title,
        String summary,
        String coverImageUrl,
        String category,
        String authorName,
        LocalDateTime publishedAt
) {
    public static BlogPostSummaryResponse from(BlogPost p) {
        return new BlogPostSummaryResponse(
                p.getId(), p.getSlug(), p.getTitle(), p.getSummary(),
                p.getCoverImageUrl(), p.getCategory(), p.getAuthorName(),
                p.getPublishedAt()
        );
    }
}
