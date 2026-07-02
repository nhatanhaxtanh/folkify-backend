package com.folkify.blog.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blog_posts")
public class BlogPost extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String coverImageUrl;

    private String category;

    private String authorName;

    @Column(nullable = false)
    private boolean published = false;

    private LocalDateTime publishedAt;

    public BlogPost() {}

    public String getSlug() { return slug; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getContent() { return content; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public String getCategory() { return category; }
    public String getAuthorName() { return authorName; }
    public boolean isPublished() { return published; }
    public LocalDateTime getPublishedAt() { return publishedAt; }

    public void setSlug(String slug) { this.slug = slug; }
    public void setTitle(String title) { this.title = title; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setContent(String content) { this.content = content; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public void setCategory(String category) { this.category = category; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public void setPublished(boolean published) { this.published = published; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
}
