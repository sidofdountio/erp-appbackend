SELECT 1 + 1;

-- 1. PERMISSION TABLE
-- Matches @GeneratedValue (Sequence)
CREATE SEQUENCE IF NOT EXISTS permission_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE permission
(
    id   BIGINT       NOT NULL,
    name VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT pk_permission PRIMARY KEY (id)
);

ALTER TABLE permission
    ALTER COLUMN id SET DEFAULT nextval('permission_seq');

-- 2. ROLE TABLE
-- Matches @GeneratedValue(strategy = GenerationType.IDENTITY)
CREATE TABLE role
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- 3. JOIN TABLE (ROLES_PERMISSIONS)
-- Matches @ManyToMany mapping
CREATE TABLE roles_permissions
(
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_rp_role
        FOREIGN KEY (role_id)
            REFERENCES role (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_rp_permission
        FOREIGN KEY (permission_id)
            REFERENCES permission (id)
            ON DELETE CASCADE
);



SELECT r.name as role_name, p.name as permission_name
FROM role r
         JOIN roles_permissions rp ON r.id = rp.role_id
         JOIN permission p ON rp.permission_id = p.id;