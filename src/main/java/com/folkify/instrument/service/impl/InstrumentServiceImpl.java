package com.folkify.instrument.service.impl;

import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import com.folkify.instrument.dto.*;
import com.folkify.instrument.entity.Instrument;
import com.folkify.instrument.repository.InstrumentRepository;
import com.folkify.instrument.repository.LessonRepository;
import com.folkify.instrument.service.InstrumentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InstrumentServiceImpl implements InstrumentService {

    private final InstrumentRepository instrumentRepository;
    private final LessonRepository lessonRepository;

    public InstrumentServiceImpl(InstrumentRepository instrumentRepository, LessonRepository lessonRepository) {
        this.instrumentRepository = instrumentRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<InstrumentSummaryResponse> getAllInstruments() {
        return instrumentRepository.findAllByOrderByPopularityDesc()
                .stream()
                .map(InstrumentSummaryResponse::from)
                .toList();
    }

    @Override
    public InstrumentDetailResponse getInstrumentBySlug(String slug) {
        Instrument instrument = instrumentRepository.findBySlugWithLessons(slug)
                .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
        instrument.getFacts().size();
        instrument.getSongs().size();
        return InstrumentDetailResponse.from(instrument);
    }

    @Override
    public List<LessonSummaryResponse> getLessonsByInstrument(String slug) {
        if (!instrumentRepository.findBySlug(slug).isPresent()) {
            throw new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND);
        }
        return lessonRepository.findByInstrumentSlugOrderByOrderIndexAsc(slug)
                .stream()
                .map(LessonSummaryResponse::from)
                .toList();
    }

    @Override
    public LessonDetailResponse getLessonDetail(String instrumentSlug, String lessonSlug) {
        var lesson = lessonRepository.findBySlugAndInstrumentSlug(lessonSlug, instrumentSlug)
                .orElseThrow(() -> new ApiException(ErrorCode.LESSON_NOT_FOUND));
        // trigger lazy load của steps và tips trong transaction
        lesson.getSteps().size();
        lesson.getTips().size();
        return LessonDetailResponse.from(lesson);
    }

    @Override
    public List<SongResponse> getSongsByInstrument(String slug) {
        Instrument instrument = instrumentRepository.findBySlug(slug)
                .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
        instrument.getSongs().size();
        return instrument.getSongs().stream().map(SongResponse::from).toList();
    }
}
