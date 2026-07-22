CREATE TABLE payment_transaction (
    id                    UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id               UUID          NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    target_plan           VARCHAR(20)   NOT NULL,
    gateway_reference_id  VARCHAR(100)  NOT NULL UNIQUE,
    bank_transaction_code VARCHAR(100),
    amount                NUMERIC(19,2) NOT NULL,
    transfer_content      TEXT          NOT NULL,
    transaction_date      TIMESTAMP     NOT NULL,
    status                VARCHAR(50)   NOT NULL,
    created_at            TIMESTAMP     NOT NULL DEFAULT now(),
    updated_at            TIMESTAMP
);

CREATE INDEX idx_payment_txn_transfer_content ON payment_transaction (transfer_content);
CREATE INDEX idx_payment_txn_status ON payment_transaction (status);
CREATE INDEX idx_payment_txn_user ON payment_transaction (user_id);

CREATE TABLE payment_webhook_log (
    id                UUID         NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    gateway           VARCHAR(50),
    raw_payload       TEXT         NOT NULL,
    processing_status VARCHAR(50),
    error_message     TEXT,
    client_ip         VARCHAR(50),
    created_at        TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at        TIMESTAMP
);
