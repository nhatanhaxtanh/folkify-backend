package com.folkify.instrument.repository;

import com.folkify.instrument.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<Lesson, UUID> {

    List<Lesson> findByInstrumentSlugOrderByOrderIndexAsc(String slug);
    List<Lesson> findByInstrumentIdOrderByOrderIndexAsc(UUID instrumentId);

    @Query("SELECT l FROM Lesson l WHERE l.slug = :lessonSlug AND l.instrument.slug = :instrumentSlug")
    Optional<Lesson> findBySlugAndInstrumentSlug(
            @Param("lessonSlug") String lessonSlug,
            @Param("instrumentSlug") String instrumentSlug
    );

    long countByInstrumentId(UUID instrumentId);
}
