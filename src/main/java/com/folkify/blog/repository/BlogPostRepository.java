package com.folkify.blog.repository;

import com.folkify.blog.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
    List<BlogPost> findByPublishedTrueOrderByPublishedAtDesc();
    Optional<BlogPost> findBySlugAndPublishedTrue(String slug);
    boolean existsBySlug(String slug);
}
