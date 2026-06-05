CREATE TABLE IF NOT EXISTS instruments (
    id           UUID         PRIMARY KEY,
    slug         VARCHAR(100) NOT NULL UNIQUE,
    name         VARCHAR(255) NOT NULL,
    english_name VARCHAR(255),
    region       VARCHAR(255),
    category     VARCHAR(100),
    emoji        VARCHAR(10),
    color        VARCHAR(20),
    image_url    TEXT,
    short_desc   TEXT,
    description  TEXT,
    origin       TEXT,
    material     TEXT,
    sound_range  VARCHAR(100),
    difficulty   INTEGER      NOT NULL DEFAULT 1,
    popularity   INTEGER      NOT NULL DEFAULT 1,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS instrument_facts (
    instrument_id UUID    NOT NULL REFERENCES instruments(id) ON DELETE CASCADE,
    fact          TEXT    NOT NULL,
    order_index   INTEGER NOT NULL,
    PRIMARY KEY (instrument_id, order_index)
);

CREATE TABLE IF NOT EXISTS lessons (
    id            UUID         PRIMARY KEY,
    instrument_id UUID         NOT NULL REFERENCES instruments(id) ON DELETE CASCADE,
    slug          VARCHAR(100) NOT NULL UNIQUE,
    title         VARCHAR(255) NOT NULL,
    duration      VARCHAR(50),
    level         VARCHAR(50),
    description   TEXT,
    xp            INTEGER      NOT NULL DEFAULT 0,
    youtube_url   TEXT,
    order_index   INTEGER      NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP,
    CONSTRAINT fk_lesson_instrument FOREIGN KEY (instrument_id) REFERENCES instruments(id)
);

CREATE TABLE IF NOT EXISTS lesson_steps (
    lesson_id   UUID    NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    step        TEXT    NOT NULL,
    order_index INTEGER NOT NULL,
    PRIMARY KEY (lesson_id, order_index)
);

CREATE TABLE IF NOT EXISTS lesson_tips (
    lesson_id   UUID    NOT NULL REFERENCES lessons(id) ON DELETE CASCADE,
    tip         TEXT    NOT NULL,
    order_index INTEGER NOT NULL,
    PRIMARY KEY (lesson_id, order_index)
);

CREATE TABLE IF NOT EXISTS songs (
    id            UUID         PRIMARY KEY,
    instrument_id UUID         NOT NULL REFERENCES instruments(id) ON DELETE CASCADE,
    title         VARCHAR(255) NOT NULL,
    artist        VARCHAR(255),
    duration      VARCHAR(20),
    order_index   INTEGER      NOT NULL DEFAULT 0,
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_instruments_slug    ON instruments(slug);
CREATE INDEX IF NOT EXISTS idx_lessons_instrument  ON lessons(instrument_id);
CREATE INDEX IF NOT EXISTS idx_lessons_slug        ON lessons(slug);
CREATE INDEX IF NOT EXISTS idx_songs_instrument    ON songs(instrument_id);
