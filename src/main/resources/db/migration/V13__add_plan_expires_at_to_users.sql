-- Ngày hết hạn gói trả phí. NULL = không giới hạn (gói cũ / chưa mua).
ALTER TABLE users ADD COLUMN plan_expires_at TIMESTAMP;
