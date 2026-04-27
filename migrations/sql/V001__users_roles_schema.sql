--liquibase formatted sql

--changeset gorlov:001-users-roles
CREATE TABLE IF NOT EXISTS users (
    id uuid PRIMARY KEY,
    login varchar(255) UNIQUE NOT NULL,
    password_hash varchar(64) NOT NULL,
    birth_date date NOT NULL,
    email varchar(255) UNIQUE NOT NULL CHECK (email ~ '^[a-zA-Z0-9+]+@[a-zA-Z]+\.[a-zA-Z]{2,}$'),
    created_at timestamptz NOT NULL,
    updated_at timestamptz NOT NULL,
    removed bool NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS roles (
    id serial PRIMARY KEY,
    name varchar(255) UNIQUE NOT NULL
);

INSERT INTO roles (name)
VALUES
    ('Client'),
    ('Store manager'),
    ('Warehouse manager'),
    ('System Admin')
ON CONFLICT DO NOTHING;

CREATE TABLE IF NOT EXISTS users_roles (
    user_id uuid NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

ALTER TABLE IF EXISTS users_roles
    ADD CONSTRAINT fk_users_roles_user
    FOREIGN KEY (user_id) REFERENCES users (id) DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE IF EXISTS users_roles
    ADD CONSTRAINT fk_users_roles_role
    FOREIGN KEY (role_id) REFERENCES roles (id) DEFERRABLE INITIALLY IMMEDIATE;

--rollback ALTER TABLE IF EXISTS users_roles DROP CONSTRAINT IF EXISTS fk_users_roles_role;
--rollback ALTER TABLE IF EXISTS users_roles DROP CONSTRAINT IF EXISTS fk_users_roles_user;
--rollback DROP TABLE IF EXISTS users_roles;
--rollback DROP TABLE IF EXISTS roles;
--rollback DROP TABLE IF EXISTS users;
