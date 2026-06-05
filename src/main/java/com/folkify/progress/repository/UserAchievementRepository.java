package com.folkify.progress.repository;

import com.folkify.progress.entity.UserAchievement;
import com.folkify.progress.entity.UserAchievementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, UserAchievementId> {

    @Query("SELECT ua FROM UserAchievement ua JOIN FETCH ua.achievement WHERE ua.id.userId = :userId")
    List<UserAchievement> findByUserIdWithAchievement(@Param("userId") UUID userId);

    boolean existsByIdUserIdAndIdAchievementId(UUID userId, UUID achievementId);
}
