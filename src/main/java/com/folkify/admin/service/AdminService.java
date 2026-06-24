package com.folkify.admin.service;

import com.folkify.admin.dto.*;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.Role;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    // Users
    AdminStatsResponse getStats();
    List<AdminUserResponse> getAllUsers();
    AdminUserResponse updateUserPlan(UUID userId, Plan plan);
    AdminUserResponse updateUserRole(UUID userId, Role role);
    void deleteUser(UUID userId);

    // Instruments
    List<InstrumentAdminResponse> getAllInstruments();
    InstrumentAdminResponse updateInstrument(UUID id, InstrumentUpdateRequest request);

    // Lessons
    List<LessonAdminResponse> getLessons(UUID instrumentId);
    LessonAdminResponse createLesson(LessonRequest request);
    LessonAdminResponse updateLesson(UUID id, LessonRequest request);
    void deleteLesson(UUID id);

    // Songs
    List<SongAdminResponse> getSongs(UUID instrumentId);
    SongAdminResponse createSong(SongRequest request);
    SongAdminResponse updateSong(UUID id, SongRequest request);
    void deleteSong(UUID id);

    // Sheet Music
    List<SheetMusicResponse> getSheets(UUID instrumentId);
    SheetMusicResponse createSheet(SheetMusicRequest request);
    SheetMusicResponse updateSheet(UUID id, SheetMusicRequest request);
    void deleteSheet(UUID id);
}
