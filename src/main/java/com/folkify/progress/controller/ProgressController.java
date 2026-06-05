package com.folkify.progress.controller;

import com.folkify.auth.entity.User;
import com.folkify.common.response.ApiResponse;
import com.folkify.progress.dto.*;
import com.folkify.progress.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@Tag(name = "Progress", description = "API tiến độ học tập và thành tích")
@SecurityRequirement(name = "bearerAuth")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/lessons/{lessonId}/complete")
    @Operation(summary = "Đánh dấu bài học đã hoàn thành")
    public ResponseEntity<ApiResponse<CompleteLessonResponse>> completeLesson(
            @PathVariable UUID lessonId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(progressService.completeLesson(lessonId, user)));
    }

    @GetMapping
    @Operation(summary = "Tổng quan tiến độ học tập")
    public ResponseEntity<ApiResponse<UserProgressResponse>> getProgress(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(progressService.getProgress(user)));
    }

    @GetMapping("/achievements")
    @Operation(summary = "Danh sách thành tích")
    public ResponseEntity<ApiResponse<AchievementsResponse>> getAchievements(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(progressService.getAchievements(user)));
    }

    @GetMapping("/activity")
    @Operation(summary = "Hoạt động 7 ngày gần nhất")
    public ResponseEntity<ApiResponse<List<ActivityDayDto>>> getWeeklyActivity(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(progressService.getWeeklyActivity(user)));
    }
}
