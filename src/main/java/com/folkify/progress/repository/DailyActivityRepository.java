package com.folkify.progress.repository;

import com.folkify.progress.entity.DailyActivity;
import com.folkify.progress.entity.DailyActivityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DailyActivityRepository extends JpaRepository<DailyActivity, DailyActivityId> {

    Optional<DailyActivity> findByIdUserIdAndIdActivityDate(UUID userId, LocalDate date);

    @Query("""
        SELECT da FROM DailyActivity da
        WHERE da.id.userId = :userId AND da.id.activityDate >= :from
        ORDER BY da.id.activityDate ASC
    """)
    List<DailyActivity> findFromDateByUserId(@Param("userId") UUID userId, @Param("from") LocalDate from);

    @Query("""
        SELECT da.id.activityDate FROM DailyActivity da
        WHERE da.id.userId = :userId
        ORDER BY da.id.activityDate DESC
    """)
    List<LocalDate> findAllActivityDatesByUserId(@Param("userId") UUID userId);
}
