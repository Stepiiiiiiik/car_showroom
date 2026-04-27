--liquibase formatted sql

--changeset gorlov:005-test-drives
CREATE TABLE IF NOT EXISTS test_drives (
    id uuid PRIMARY KEY,
    car_id uuid NOT NULL,
    client_id uuid NOT NULL,
    start timestamptz NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    removed bool NOT NULL DEFAULT false
);

ALTER TABLE IF EXISTS test_drives
    ADD CONSTRAINT fk_test_drives_car
    FOREIGN KEY (car_id) REFERENCES cars (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS test_drives
    ADD CONSTRAINT fk_test_drives_client
    FOREIGN KEY (client_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

--rollback ALTER TABLE IF EXISTS test_drives DROP CONSTRAINT IF EXISTS fk_test_drives_client;
--rollback ALTER TABLE IF EXISTS test_drives DROP CONSTRAINT IF EXISTS fk_test_drives_car;
--rollback DROP TABLE IF EXISTS test_drives;
