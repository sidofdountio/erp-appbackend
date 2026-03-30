DROP TABLE IF EXISTS category CASCADE;
CREATE TABLE IF NOT EXISTS category
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,

    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL,
    created_by       VARCHAR(255),
    last_modified_by VARCHAR(255)
);

-- PRODUCT----------------------
DROP TABLE IF EXISTS product CASCADE;
CREATE TABLE IF NOT EXISTS product
(
    id                 UUID             NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    name               VARCHAR(255)     NOT NULL UNIQUE,
    price              NUMERIC(32, 2),
    available_quantity DOUBLE PRECISION NOT NULL,
    active             BOOLEAN                               DEFAULT TRUE,
    promotion           BOOLEAN                                         DEFAULT FALSE,
    sku                VARCHAR(255)     NOT NULL UNIQUE,


    shop_id            UUID             NOT NULL UNIQUE,
    category_id UUID
        CONSTRAINT fk_product_category REFERENCES category (id),

    created_at         TIMESTAMP        NOT NULL,
    updated_at         TIMESTAMP        NOT NULL,
    created_by         VARCHAR(255),
    last_modified_by   VARCHAR(255)
);


-- CREATE INDEX idx_product_shop_id ON product (shop_id);
-- CREATE UNIQUE INDEX idx_product_sku ON product (sku);
-- CREATE INDEX idx_product_category_id ON product (category_id);
