CREATE TABLE IF NOT EXISTS user_lesson_progress (
    user_id         UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    lesson_id       UUID        NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    completed_at    TIMESTAMP   NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, lesson_id)
);

CREATE TABLE IF NOT EXISTS achievements (
    id          UUID         PRIMARY KEY,
    slug        VARCHAR(100) NOT NULL UNIQUE,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    icon        VARCHAR(100),
    type        VARCHAR(50)  NOT NULL,
    threshold   INTEGER      NOT NULL DEFAULT 1,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_achievements (
    user_id         UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    achievement_id  UUID        NOT NULL REFERENCES achievements(id) ON DELETE CASCADE,
    unlocked_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id, achievement_id)
);

CREATE TABLE IF NOT EXISTS daily_activities (
    user_id         UUID        NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    activity_date   DATE        NOT NULL,
    xp_earned       INTEGER     NOT NULL DEFAULT 0,
    minutes         INTEGER     NOT NULL DEFAULT 0,
    PRIMARY KEY (user_id, activity_date)
);

CREATE INDEX IF NOT EXISTS idx_user_lesson_progress_user ON user_lesson_progress(user_id);
CREATE INDEX IF NOT EXISTS idx_user_achievements_user    ON user_achievements(user_id);
CREATE INDEX IF NOT EXISTS idx_daily_activities_user     ON daily_activities(user_id);
