package com.folkify.progress.dto;

import java.util.List;

public class AchievementsResponse {

    private List<AchievementDto> unlocked;
    private List<AchievementDto> locked;

    private AchievementsResponse() {}

    public static AchievementsResponse of(List<AchievementDto> unlocked, List<AchievementDto> locked) {
        AchievementsResponse r = new AchievementsResponse();
        r.unlocked = unlocked;
        r.locked = locked;
        return r;
    }

    public List<AchievementDto> getUnlocked() { return unlocked; }
    public List<AchievementDto> getLocked() { return locked; }
}
