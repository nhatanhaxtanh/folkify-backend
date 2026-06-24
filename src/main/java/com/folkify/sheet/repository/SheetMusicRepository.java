package com.folkify.sheet.repository;

import com.folkify.sheet.entity.SheetMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SheetMusicRepository extends JpaRepository<SheetMusic, UUID> {
    List<SheetMusic> findAllByOrderByCreatedAtDesc();
}
