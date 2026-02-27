-- ===================================
--   SCHEMA CREATE CUSTOMER DB
-- ====================================

DROP TABLE IF EXISTS customer CASCADE;
CREATE TABLE IF NOT EXISTS customer
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name             VARCHAR(255) NOT NULL,
    email            VARCHAR(255) NOT NULL,
    address          VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL,
    created_by       VARCHAR(255),
    last_modified_by VARCHAR(255)
);
