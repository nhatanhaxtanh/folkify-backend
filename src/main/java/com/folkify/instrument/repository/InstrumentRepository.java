package com.folkify.instrument.repository;

import com.folkify.instrument.entity.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstrumentRepository extends JpaRepository<Instrument, UUID> {

    List<Instrument> findAllByOrderByPopularityDesc();

    Optional<Instrument> findBySlug(String slug);

    @Query("SELECT i FROM Instrument i LEFT JOIN FETCH i.lessons l WHERE i.slug = :slug")
    Optional<Instrument> findBySlugWithLessons(@Param("slug") String slug);
}
