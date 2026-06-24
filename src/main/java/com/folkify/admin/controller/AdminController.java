package com.folkify.admin.controller;

import com.folkify.admin.dto.*;
import com.folkify.admin.service.AdminService;
import com.folkify.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "API quản trị hệ thống")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ── Stats & Users ──────────────────────────────────────────────────────

    @GetMapping("/stats")
    @Operation(summary = "Thống kê tổng quan")
    public ResponseEntity<ApiResponse<AdminStatsResponse>> getStats() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getStats()));
    }

    @GetMapping("/users")
    @Operation(summary = "Danh sách tất cả người dùng")
    public ResponseEntity<ApiResponse<List<AdminUserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllUsers()));
    }

    @PatchMapping("/users/{id}/plan")
    @Operation(summary = "Đổi gói của người dùng")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updatePlan(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePlanRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật gói thành công", adminService.updateUserPlan(id, request.plan())));
    }

    @PatchMapping("/users/{id}/role")
    @Operation(summary = "Đổi role của người dùng")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật role thành công", adminService.updateUserRole(id, request.role())));
    }

    @DeleteMapping("/users/{id}")
    @Operation(summary = "Xóa người dùng")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa người dùng thành công", null));
    }

    // ── Instruments ────────────────────────────────────────────────────────

    @GetMapping("/instruments")
    @Operation(summary = "Danh sách nhạc cụ")
    public ResponseEntity<ApiResponse<List<InstrumentAdminResponse>>> getAllInstruments() {
        return ResponseEntity.ok(ApiResponse.success(adminService.getAllInstruments()));
    }

    @PatchMapping("/instruments/{id}")
    @Operation(summary = "Cập nhật thông tin nhạc cụ")
    public ResponseEntity<ApiResponse<InstrumentAdminResponse>> updateInstrument(
            @PathVariable UUID id,
            @RequestBody InstrumentUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật nhạc cụ thành công", adminService.updateInstrument(id, request)));
    }

    // ── Lessons ────────────────────────────────────────────────────────────

    @GetMapping("/lessons")
    @Operation(summary = "Danh sách bài học")
    public ResponseEntity<ApiResponse<List<LessonAdminResponse>>> getLessons(
            @RequestParam(required = false) UUID instrumentId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getLessons(instrumentId)));
    }

    @PostMapping("/lessons")
    @Operation(summary = "Tạo bài học mới")
    public ResponseEntity<ApiResponse<LessonAdminResponse>> createLesson(
            @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo bài học thành công", adminService.createLesson(request)));
    }

    @PatchMapping("/lessons/{id}")
    @Operation(summary = "Cập nhật bài học")
    public ResponseEntity<ApiResponse<LessonAdminResponse>> updateLesson(
            @PathVariable UUID id,
            @RequestBody LessonRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật bài học thành công", adminService.updateLesson(id, request)));
    }

    @DeleteMapping("/lessons/{id}")
    @Operation(summary = "Xóa bài học")
    public ResponseEntity<ApiResponse<Void>> deleteLesson(@PathVariable UUID id) {
        adminService.deleteLesson(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa bài học thành công", null));
    }

    // ── Songs ──────────────────────────────────────────────────────────────

    @GetMapping("/songs")
    @Operation(summary = "Danh sách bài hát")
    public ResponseEntity<ApiResponse<List<SongAdminResponse>>> getSongs(
            @RequestParam(required = false) UUID instrumentId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getSongs(instrumentId)));
    }

    @PostMapping("/songs")
    @Operation(summary = "Tạo bài hát mới")
    public ResponseEntity<ApiResponse<SongAdminResponse>> createSong(
            @Valid @RequestBody SongRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo bài hát thành công", adminService.createSong(request)));
    }

    @PatchMapping("/songs/{id}")
    @Operation(summary = "Cập nhật bài hát")
    public ResponseEntity<ApiResponse<SongAdminResponse>> updateSong(
            @PathVariable UUID id,
            @RequestBody SongRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật bài hát thành công", adminService.updateSong(id, request)));
    }

    @DeleteMapping("/songs/{id}")
    @Operation(summary = "Xóa bài hát")
    public ResponseEntity<ApiResponse<Void>> deleteSong(@PathVariable UUID id) {
        adminService.deleteSong(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa bài hát thành công", null));
    }

    // ── Sheet Music ────────────────────────────────────────────────────────

    @GetMapping("/sheets")
    @Operation(summary = "Danh sách sheet nhạc")
    public ResponseEntity<ApiResponse<List<SheetMusicResponse>>> getSheets(
            @RequestParam(required = false) UUID instrumentId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getSheets(instrumentId)));
    }

    @PostMapping("/sheets")
    @Operation(summary = "Tạo sheet nhạc mới")
    public ResponseEntity<ApiResponse<SheetMusicResponse>> createSheet(
            @Valid @RequestBody SheetMusicRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Tạo sheet nhạc thành công", adminService.createSheet(request)));
    }

    @PatchMapping("/sheets/{id}")
    @Operation(summary = "Cập nhật sheet nhạc")
    public ResponseEntity<ApiResponse<SheetMusicResponse>> updateSheet(
            @PathVariable UUID id,
            @RequestBody SheetMusicRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật sheet nhạc thành công", adminService.updateSheet(id, request)));
    }

    @DeleteMapping("/sheets/{id}")
    @Operation(summary = "Xóa sheet nhạc")
    public ResponseEntity<ApiResponse<Void>> deleteSheet(@PathVariable UUID id) {
        adminService.deleteSheet(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sheet nhạc thành công", null));
    }
}
