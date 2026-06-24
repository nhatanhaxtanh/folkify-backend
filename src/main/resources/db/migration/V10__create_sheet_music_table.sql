CREATE TABLE sheet_music (
    id          UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255),
    genre       VARCHAR(100),
    difficulty  VARCHAR(50),
    pages       INT          NOT NULL DEFAULT 1,
    is_premium  BOOLEAN      NOT NULL DEFAULT FALSE,
    file_url    TEXT,
    description TEXT,
    instrument_id UUID REFERENCES instruments(id) ON DELETE SET NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP
);
