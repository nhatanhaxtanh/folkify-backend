package com.folkify.instrument.service;

import com.folkify.instrument.dto.*;

import java.util.List;

public interface InstrumentService {
    List<InstrumentSummaryResponse> getAllInstruments();
    InstrumentDetailResponse getInstrumentBySlug(String slug);
    List<LessonSummaryResponse> getLessonsByInstrument(String slug);
    LessonDetailResponse getLessonDetail(String instrumentSlug, String lessonSlug);
    List<SongResponse> getSongsByInstrument(String slug);
}
