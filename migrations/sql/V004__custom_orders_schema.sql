--liquibase formatted sql

--changeset gorlov:004-custom-orders
CREATE TABLE IF NOT EXISTS custom_orders (
    id uuid PRIMARY KEY,
    car_id uuid NOT NULL,
    client_id uuid NOT NULL,
    manager_id uuid NOT NULL,
    price decimal(7, 3) NOT NULL,
    state smallint NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    removed bool NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS custom_order_parts (
    order_id uuid NOT NULL,
    spare_part_id uuid NOT NULL,
    PRIMARY KEY (order_id, spare_part_id)
);

ALTER TABLE IF EXISTS custom_orders
    ADD CONSTRAINT fk_custom_orders_car
    FOREIGN KEY (car_id) REFERENCES cars (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS custom_orders
    ADD CONSTRAINT fk_custom_orders_client
    FOREIGN KEY (client_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS custom_orders
    ADD CONSTRAINT fk_custom_orders_manager
    FOREIGN KEY (manager_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS custom_order_parts
    ADD CONSTRAINT fk_custom_order_parts_order
    FOREIGN KEY (order_id) REFERENCES custom_orders (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS custom_order_parts
    ADD CONSTRAINT fk_custom_order_parts_spare
    FOREIGN KEY (spare_part_id) REFERENCES spare_parts (id) DEFERRABLE INITIALLY IMMEDIATE;

--rollback ALTER TABLE IF EXISTS custom_order_parts DROP CONSTRAINT IF EXISTS fk_custom_order_parts_spare;
--rollback ALTER TABLE IF EXISTS custom_order_parts DROP CONSTRAINT IF EXISTS fk_custom_order_parts_order;
--rollback ALTER TABLE IF EXISTS custom_orders DROP CONSTRAINT IF EXISTS fk_custom_orders_manager;
--rollback ALTER TABLE IF EXISTS custom_orders DROP CONSTRAINT IF EXISTS fk_custom_orders_client;
--rollback ALTER TABLE IF EXISTS custom_orders DROP CONSTRAINT IF EXISTS fk_custom_orders_car;
--rollback DROP TABLE IF EXISTS custom_order_parts;
--rollback DROP TABLE IF EXISTS custom_orders;
