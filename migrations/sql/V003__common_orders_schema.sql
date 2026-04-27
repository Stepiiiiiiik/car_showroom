--liquibase formatted sql

--changeset gorlov:003-common-orders
CREATE TABLE IF NOT EXISTS common_orders (
    id uuid PRIMARY KEY,
    car_id uuid NOT NULL,
    client_id uuid NOT NULL,
    manager_id uuid NOT NULL,
    price decimal(7, 3) NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    state smallint NOT NULL,
    removed bool NOT NULL DEFAULT false
);

ALTER TABLE IF EXISTS common_orders
    ADD CONSTRAINT fk_common_orders_car
    FOREIGN KEY (car_id) REFERENCES cars (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS common_orders
    ADD CONSTRAINT fk_common_orders_client
    FOREIGN KEY (client_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS common_orders
    ADD CONSTRAINT fk_common_orders_manager
    FOREIGN KEY (manager_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

--rollback ALTER TABLE IF EXISTS common_orders DROP CONSTRAINT IF EXISTS fk_common_orders_manager;
--rollback ALTER TABLE IF EXISTS common_orders DROP CONSTRAINT IF EXISTS fk_common_orders_client;
--rollback ALTER TABLE IF EXISTS common_orders DROP CONSTRAINT IF EXISTS fk_common_orders_car;
--rollback DROP TABLE IF EXISTS common_orders;
