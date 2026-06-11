CREATE TABLE password_reset_tokens (
    id          UUID        PRIMARY KEY,
    token       VARCHAR(512) NOT NULL UNIQUE,
    user_id     UUID        NOT NULL,
    expires_at  TIMESTAMP   NOT NULL,
    used        BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP,
    CONSTRAINT fk_password_reset_token_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_prt_token ON password_reset_tokens(token);
CREATE INDEX idx_prt_user_id ON password_reset_tokens(user_id);
