package com.folkify.instrument.controller;

import com.folkify.common.response.ApiResponse;
import com.folkify.instrument.dto.*;
import com.folkify.instrument.service.InstrumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instruments")
@Tag(name = "Instruments", description = "API nhạc cụ dân tộc Việt Nam")
public class InstrumentController {

    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService instrumentService) {
        this.instrumentService = instrumentService;
    }

    @GetMapping
    @Operation(summary = "Danh sách tất cả nhạc cụ")
    public ResponseEntity<ApiResponse<List<InstrumentSummaryResponse>>> getAllInstruments() {
        return ResponseEntity.ok(ApiResponse.success(instrumentService.getAllInstruments()));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Chi tiết nhạc cụ")
    public ResponseEntity<ApiResponse<InstrumentDetailResponse>> getInstrument(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(instrumentService.getInstrumentBySlug(slug)));
    }

    @GetMapping("/{slug}/lessons")
    @Operation(summary = "Danh sách bài học của nhạc cụ")
    public ResponseEntity<ApiResponse<List<LessonSummaryResponse>>> getLessons(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(instrumentService.getLessonsByInstrument(slug)));
    }

    @GetMapping("/{slug}/lessons/{lessonSlug}")
    @Operation(summary = "Chi tiết bài học")
    public ResponseEntity<ApiResponse<LessonDetailResponse>> getLessonDetail(
            @PathVariable String slug,
            @PathVariable String lessonSlug) {
        return ResponseEntity.ok(ApiResponse.success(instrumentService.getLessonDetail(slug, lessonSlug)));
    }

    @GetMapping("/{slug}/songs")
    @Operation(summary = "Danh sách bài nhạc của nhạc cụ")
    public ResponseEntity<ApiResponse<List<SongResponse>>> getSongs(@PathVariable String slug) {
        return ResponseEntity.ok(ApiResponse.success(instrumentService.getSongsByInstrument(slug)));
    }
}
