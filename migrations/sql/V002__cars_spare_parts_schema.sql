--liquibase formatted sql

--changeset gorlov:002-cars-spare-parts
CREATE TABLE IF NOT EXISTS cars (
    id uuid PRIMARY KEY,
    brand varchar(20) NOT NULL,
    model varchar(100) NOT NULL,
    price decimal(7, 3) NOT NULL,
    body_type smallint NOT NULL,
    engine_type smallint NOT NULL,
    power integer NOT NULL,
    engine_volume decimal(2, 1) NOT NULL,
    transmission_type smallint NOT NULL,
    driveshaft_type smallint NOT NULL,
    color varchar(20) NOT NULL,
    test_drive_allowed bool NOT NULL DEFAULT false,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    removed bool NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS spare_parts (
    id uuid PRIMARY KEY,
    price decimal(7, 3) NOT NULL,
    type smallint NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    removed bool NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS car_default_spare_parts (
    car_id uuid NOT NULL,
    spare_part_id uuid NOT NULL,
    PRIMARY KEY (car_id, spare_part_id)
);

CREATE TABLE IF NOT EXISTS cars_allowed_spare_parts (
    car_id uuid NOT NULL,
    spare_part_id uuid NOT NULL,
    PRIMARY KEY (car_id, spare_part_id)
);

ALTER TABLE IF EXISTS car_default_spare_parts
    ADD CONSTRAINT fk_car_default_spare_parts_car
    FOREIGN KEY (car_id) REFERENCES cars (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS car_default_spare_parts
    ADD CONSTRAINT fk_car_default_spare_parts_spare
    FOREIGN KEY (spare_part_id) REFERENCES spare_parts (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS cars_allowed_spare_parts
    ADD CONSTRAINT fk_cars_allowed_spare_parts_car
    FOREIGN KEY (car_id) REFERENCES cars (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS cars_allowed_spare_parts
    ADD CONSTRAINT fk_cars_allowed_spare_parts_spare
    FOREIGN KEY (spare_part_id) REFERENCES spare_parts (id) DEFERRABLE INITIALLY IMMEDIATE;

--rollback ALTER TABLE IF EXISTS cars_allowed_spare_parts DROP CONSTRAINT IF EXISTS fk_cars_allowed_spare_parts_spare;
--rollback ALTER TABLE IF EXISTS cars_allowed_spare_parts DROP CONSTRAINT IF EXISTS fk_cars_allowed_spare_parts_car;
--rollback ALTER TABLE IF EXISTS car_default_spare_parts DROP CONSTRAINT IF EXISTS fk_car_default_spare_parts_spare;
--rollback ALTER TABLE IF EXISTS car_default_spare_parts DROP CONSTRAINT IF EXISTS fk_car_default_spare_parts_car;
--rollback DROP TABLE IF EXISTS cars_allowed_spare_parts;
--rollback DROP TABLE IF EXISTS car_default_spare_parts;
--rollback DROP TABLE IF EXISTS spare_parts;
--rollback DROP TABLE IF EXISTS cars;
