--liquibase formatted sql

--changeset gorlov:007-users-roles

ALTER TABLE IF EXISTS users_roles DROP CONSTRAINT IF EXISTS fk_users_roles_role;
ALTER TABLE IF EXISTS users_roles DROP CONSTRAINT IF EXISTS fk_users_roles_user;

DROP TABLE IF EXISTS roles;

DROP TABLE IF EXISTS users_roles;

ALTER TABLE IF EXISTS users
DROP COLUMN login,
    DROP COLUMN password_hash,
    DROP COLUMN birth_date,
    DROP COLUMN email,
    DROP COLUMN created_at,
    DROP COLUMN updated_at,
    DROP COLUMN removed;

-- rollback DROP TABLE IF EXISTS users;
