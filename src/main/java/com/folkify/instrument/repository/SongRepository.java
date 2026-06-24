package com.folkify.instrument.repository;

import com.folkify.instrument.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SongRepository extends JpaRepository<Song, UUID> {
    List<Song> findByInstrumentIdOrderByOrderIndexAsc(UUID instrumentId);
}
