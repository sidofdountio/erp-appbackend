-- ===================================
--   SCHEMA CREATE ORDERS DB
-- ====================================


-- ===================================
--   ORDER TABLE
-- ====================================

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE IF NOT EXISTS orders
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    totalAmount      NUMERIC(32, 2),

    reference        VARCHAR(255),
    payment_method   VARCHAR(255) NOT NULL,

    customer_id      UUID         NOT NULL UNIQUE,
    shop_id          UUID         NOT NULL UNIQUE,

    created_at       TIMESTAMP    NOT NULL,
    updated_at       TIMESTAMP    NOT NULL,
    created_by       VARCHAR(255),
    last_modified_by VARCHAR(255)
);

-- ===================================
--   ORDER_TEME TABLE
-- ====================================

CREATE TABLE order_items
(
    id                UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    quantity          INTEGER        NOT NULL,
    price_at_purchase NUMERIC(32, 2) NOT NULL,

    product_id        UUID           NOT NULL,
    order_id          UUID
        CONSTRAINT fk_order_order_item REFERENCES orders (id) ON DELETE CASCADE
);

CREATE INDEX idx_orders_customer ON orders (customer_id);
CREATE INDEX idx_orders_shop ON orders (shop_id);
-- Optimization: This index allows you to find all items for an order instantly
CREATE INDEX idx_order_items_order_id ON order_items (order_id);
-- Optimization: Useful for analytics (seeing how many times a product was sold)
CREATE INDEX idx_order_items_product_id ON order_items (product_id);
