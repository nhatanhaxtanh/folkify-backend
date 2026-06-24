package com.folkify.admin.service.impl;

import com.folkify.admin.dto.*;
import com.folkify.admin.service.AdminService;
import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.Role;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.UserRepository;
import com.folkify.common.exception.ApiException;
import com.folkify.common.exception.ErrorCode;
import com.folkify.instrument.entity.Instrument;
import com.folkify.instrument.entity.Lesson;
import com.folkify.instrument.entity.Song;
import com.folkify.instrument.repository.InstrumentRepository;
import com.folkify.instrument.repository.LessonRepository;
import com.folkify.instrument.repository.SongRepository;
import com.folkify.sheet.entity.SheetMusic;
import com.folkify.sheet.repository.SheetMusicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final InstrumentRepository instrumentRepository;
    private final LessonRepository lessonRepository;
    private final SongRepository songRepository;
    private final SheetMusicRepository sheetMusicRepository;

    public AdminServiceImpl(UserRepository userRepository,
                            InstrumentRepository instrumentRepository,
                            LessonRepository lessonRepository,
                            SongRepository songRepository,
                            SheetMusicRepository sheetMusicRepository) {
        this.userRepository = userRepository;
        this.instrumentRepository = instrumentRepository;
        this.lessonRepository = lessonRepository;
        this.songRepository = songRepository;
        this.sheetMusicRepository = sheetMusicRepository;
    }

    // ── Users ──────────────────────────────────────────────────────────────

    @Override
    public AdminStatsResponse getStats() {
        List<User> all = userRepository.findAll();
        LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);

        long totalUsers  = all.size();
        long freeUsers   = all.stream().filter(u -> u.getPlan() == Plan.FREE).count();
        long basicUsers  = all.stream().filter(u -> u.getPlan() == Plan.BASIC).count();
        long proUsers    = all.stream().filter(u -> u.getPlan() == Plan.PRO).count();
        long newThisWeek = all.stream().filter(u -> u.getCreatedAt() != null && u.getCreatedAt().isAfter(weekAgo)).count();
        long totalAdmins = all.stream().filter(u -> u.getRole() == Role.ADMIN).count();

        return new AdminStatsResponse(totalUsers, freeUsers, basicUsers, proUsers, newThisWeek, totalAdmins);
    }

    @Override
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .sorted((a, b) -> {
                    if (a.getCreatedAt() == null) return 1;
                    if (b.getCreatedAt() == null) return -1;
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .map(AdminUserResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserPlan(UUID userId, Plan plan) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        user.setPlan(plan);
        return AdminUserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserRole(UUID userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
        user.setRole(role);
        return AdminUserResponse.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }

    // ── Instruments ────────────────────────────────────────────────────────

    @Override
    public List<InstrumentAdminResponse> getAllInstruments() {
        return instrumentRepository.findAllByOrderByPopularityDesc().stream()
                .map(InstrumentAdminResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public InstrumentAdminResponse updateInstrument(UUID id, InstrumentUpdateRequest req) {
        Instrument inst = instrumentRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
        if (req.name() != null)        inst.setName(req.name());
        if (req.englishName() != null)  inst.setEnglishName(req.englishName());
        if (req.region() != null)       inst.setRegion(req.region());
        if (req.category() != null)     inst.setCategory(req.category());
        if (req.emoji() != null)        inst.setEmoji(req.emoji());
        if (req.color() != null)        inst.setColor(req.color());
        if (req.imageUrl() != null)     inst.setImageUrl(req.imageUrl());
        if (req.shortDesc() != null)    inst.setShortDesc(req.shortDesc());
        if (req.description() != null)  inst.setDescription(req.description());
        if (req.origin() != null)       inst.setOrigin(req.origin());
        if (req.material() != null)     inst.setMaterial(req.material());
        if (req.soundRange() != null)   inst.setSoundRange(req.soundRange());
        if (req.difficulty() != null)   inst.setDifficulty(req.difficulty());
        if (req.popularity() != null)   inst.setPopularity(req.popularity());
        if (req.facts() != null)        inst.setFacts(req.facts());
        return InstrumentAdminResponse.from(instrumentRepository.save(inst));
    }

    // ── Lessons ────────────────────────────────────────────────────────────

    @Override
    public List<LessonAdminResponse> getLessons(UUID instrumentId) {
        List<Lesson> lessons = instrumentId != null
                ? lessonRepository.findByInstrumentIdOrderByOrderIndexAsc(instrumentId)
                : lessonRepository.findAll();
        return lessons.stream().map(LessonAdminResponse::from).toList();
    }

    @Override
    @Transactional
    public LessonAdminResponse createLesson(LessonRequest req) {
        Instrument inst = instrumentRepository.findById(req.instrumentId())
                .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
        Lesson lesson = new Lesson();
        lesson.setInstrument(inst);
        lesson.setSlug(req.slug());
        lesson.setTitle(req.title());
        lesson.setDuration(req.duration());
        lesson.setLevel(req.level());
        lesson.setDescription(req.description());
        lesson.setSteps(req.steps());
        lesson.setTips(req.tips());
        lesson.setXp(req.xp());
        lesson.setYoutubeUrl(req.youtubeUrl());
        lesson.setOrderIndex(req.orderIndex());
        return LessonAdminResponse.from(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public LessonAdminResponse updateLesson(UUID id, LessonRequest req) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.LESSON_NOT_FOUND));
        if (req.instrumentId() != null) {
            Instrument inst = instrumentRepository.findById(req.instrumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
            lesson.setInstrument(inst);
        }
        if (req.slug() != null)        lesson.setSlug(req.slug());
        if (req.title() != null)       lesson.setTitle(req.title());
        if (req.duration() != null)    lesson.setDuration(req.duration());
        if (req.level() != null)       lesson.setLevel(req.level());
        if (req.description() != null) lesson.setDescription(req.description());
        if (req.steps() != null)       lesson.setSteps(req.steps());
        if (req.tips() != null)        lesson.setTips(req.tips());
        lesson.setXp(req.xp());
        if (req.youtubeUrl() != null)  lesson.setYoutubeUrl(req.youtubeUrl());
        lesson.setOrderIndex(req.orderIndex());
        return LessonAdminResponse.from(lessonRepository.save(lesson));
    }

    @Override
    @Transactional
    public void deleteLesson(UUID id) {
        if (!lessonRepository.existsById(id)) {
            throw new ApiException(ErrorCode.LESSON_NOT_FOUND);
        }
        lessonRepository.deleteById(id);
    }

    // ── Songs ──────────────────────────────────────────────────────────────

    @Override
    public List<SongAdminResponse> getSongs(UUID instrumentId) {
        List<Song> songs = instrumentId != null
                ? songRepository.findByInstrumentIdOrderByOrderIndexAsc(instrumentId)
                : songRepository.findAll();
        return songs.stream().map(SongAdminResponse::from).toList();
    }

    @Override
    @Transactional
    public SongAdminResponse createSong(SongRequest req) {
        Instrument inst = instrumentRepository.findById(req.instrumentId())
                .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
        Song song = new Song();
        song.setInstrument(inst);
        song.setTitle(req.title());
        song.setArtist(req.artist());
        song.setDuration(req.duration());
        song.setOrderIndex(req.orderIndex());
        return SongAdminResponse.from(songRepository.save(song));
    }

    @Override
    @Transactional
    public SongAdminResponse updateSong(UUID id, SongRequest req) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.SONG_NOT_FOUND));
        if (req.instrumentId() != null) {
            Instrument inst = instrumentRepository.findById(req.instrumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
            song.setInstrument(inst);
        }
        if (req.title() != null)    song.setTitle(req.title());
        if (req.artist() != null)   song.setArtist(req.artist());
        if (req.duration() != null) song.setDuration(req.duration());
        song.setOrderIndex(req.orderIndex());
        return SongAdminResponse.from(songRepository.save(song));
    }

    @Override
    @Transactional
    public void deleteSong(UUID id) {
        if (!songRepository.existsById(id)) {
            throw new ApiException(ErrorCode.SONG_NOT_FOUND);
        }
        songRepository.deleteById(id);
    }

    // ── Sheet Music ────────────────────────────────────────────────────────

    @Override
    public List<SheetMusicResponse> getSheets(UUID instrumentId) {
        List<SheetMusic> sheets = instrumentId != null
                ? sheetMusicRepository.findAll().stream()
                    .filter(s -> s.getInstrument() != null && s.getInstrument().getId().equals(instrumentId))
                    .toList()
                : sheetMusicRepository.findAllByOrderByCreatedAtDesc();
        return sheets.stream().map(SheetMusicResponse::from).toList();
    }

    @Override
    @Transactional
    public SheetMusicResponse createSheet(SheetMusicRequest req) {
        SheetMusic sheet = new SheetMusic();
        sheet.setTitle(req.title());
        sheet.setAuthor(req.author());
        sheet.setGenre(req.genre());
        sheet.setDifficulty(req.difficulty());
        sheet.setPages(req.pages());
        sheet.setPremium(req.isPremium());
        sheet.setFileUrl(req.fileUrl());
        sheet.setDescription(req.description());
        if (req.instrumentId() != null) {
            Instrument inst = instrumentRepository.findById(req.instrumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
            sheet.setInstrument(inst);
        }
        return SheetMusicResponse.from(sheetMusicRepository.save(sheet));
    }

    @Override
    @Transactional
    public SheetMusicResponse updateSheet(UUID id, SheetMusicRequest req) {
        SheetMusic sheet = sheetMusicRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.SHEET_NOT_FOUND));
        if (req.title() != null)       sheet.setTitle(req.title());
        if (req.author() != null)      sheet.setAuthor(req.author());
        if (req.genre() != null)       sheet.setGenre(req.genre());
        if (req.difficulty() != null)  sheet.setDifficulty(req.difficulty());
        sheet.setPages(req.pages());
        sheet.setPremium(req.isPremium());
        if (req.fileUrl() != null)     sheet.setFileUrl(req.fileUrl());
        if (req.description() != null) sheet.setDescription(req.description());
        if (req.instrumentId() != null) {
            Instrument inst = instrumentRepository.findById(req.instrumentId())
                    .orElseThrow(() -> new ApiException(ErrorCode.INSTRUMENT_NOT_FOUND));
            sheet.setInstrument(inst);
        }
        return SheetMusicResponse.from(sheetMusicRepository.save(sheet));
    }

    @Override
    @Transactional
    public void deleteSheet(UUID id) {
        if (!sheetMusicRepository.existsById(id)) {
            throw new ApiException(ErrorCode.SHEET_NOT_FOUND);
        }
        sheetMusicRepository.deleteById(id);
    }
}
