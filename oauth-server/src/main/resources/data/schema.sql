-- For RegisteredClientRepository

-- =============================================================
--         oauth2_registered_client table
-- =============================================================
DROP TABLE IF EXISTS oauth2_registered_client CASCADE;

CREATE TABLE oauth2_registered_client
(
    id                            varchar(100)                            NOT NULL,
    client_id                     varchar(100)                            NOT NULL,
    client_id_issued_at           timestamp     DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)  DEFAULT NULL,
    client_secret_expires_at      timestamp     DEFAULT NULL,
    client_name                   varchar(200)                            NOT NULL,
    client_authentication_methods varchar(1000)                           NOT NULL,
    authorization_grant_types     varchar(1000)                           NOT NULL,
    redirect_uris                 varchar(1000) DEFAULT NULL,
    post_logout_redirect_uris     varchar(1000) DEFAULT NULL,
    scopes                        varchar(1000)                           NOT NULL,
    client_settings               varchar(2000)                           NOT NULL,
    token_settings                varchar(2000)                           NOT NULL,
    PRIMARY KEY (id)
);

-- =============================================================
--         oauth2_authorization table
-- =============================================================

DROP TABLE IF EXISTS oauth2_authorization CASCADE;

CREATE TABLE IF NOT EXISTS oauth2_authorization
(
    id                            varchar(255) NOT NULL,
    registered_client_id          varchar(255) NOT NULL,
    principal_name                varchar(255) NOT NULL,
    authorization_grant_type      varchar(255) NOT NULL,
    authorized_scopes             varchar(1000) DEFAULT NULL,
    attributes                    text          DEFAULT NULL,
    state                         varchar(500)  DEFAULT NULL,
    authorization_code_value      text          DEFAULT NULL,
    authorization_code_issued_at  timestamp     DEFAULT NULL,
    authorization_code_expires_at timestamp     DEFAULT NULL,
    authorization_code_metadata   text          DEFAULT NULL,
    access_token_value            text          DEFAULT NULL,
    access_token_issued_at        timestamp     DEFAULT NULL,
    access_token_expires_at       timestamp     DEFAULT NULL,
    access_token_metadata         text          DEFAULT NULL,
    access_token_type             varchar(255)  DEFAULT NULL,
    access_token_scopes           varchar(1000) DEFAULT NULL,
    oidc_id_token_value           text          DEFAULT NULL,
    oidc_id_token_issued_at       timestamp     DEFAULT NULL,
    oidc_id_token_expires_at      timestamp     DEFAULT NULL,
    oidc_id_token_metadata        text          DEFAULT NULL,
    refresh_token_value           text          DEFAULT NULL,
    refresh_token_issued_at       timestamp     DEFAULT NULL,
    refresh_token_expires_at      timestamp     DEFAULT NULL,
    refresh_token_metadata        text          DEFAULT NULL,
    user_code_value               text          DEFAULT NULL,
    user_code_issued_at           timestamp     DEFAULT NULL,
    user_code_expires_at          timestamp     DEFAULT NULL,
    user_code_metadata            text          DEFAULT NULL,
    device_code_value             text          DEFAULT NULL,
    device_code_issued_at         timestamp     DEFAULT NULL,
    device_code_expires_at        timestamp     DEFAULT NULL,
    device_code_metadata          text          DEFAULT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS oauth2_authorization_consent CASCADE;

CREATE TABLE oauth2_authorization_consent
(
    registered_client_id varchar(255)  NOT NULL,
    principal_name       varchar(255)  NOT NULL,
    authorities          varchar(1000) NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);


--  OPTIONAL OAUTH1.2
ALTER TABLE oauth2_authorization
    ADD CONSTRAINT fk_oauth2_authorization_client
        FOREIGN KEY (registered_client_id)
            REFERENCES oauth2_registered_client (id)
            ON DELETE CASCADE;


ALTER TABLE oauth2_authorization_consent
    ADD CONSTRAINT fk_oauth2_consent_client
        FOREIGN KEY (registered_client_id)
            REFERENCES oauth2_registered_client (id)
            ON DELETE CASCADE;

-- =============================================================
--         PERMISSION  table
-- =============================================================
CREATE SEQUENCE IF NOT EXISTS permission_id_sequence START WITH 1 INCREMENT BY 50;

DROP TABLE IF EXISTS permission CASCADE;

CREATE TABLE IF NOT EXISTS permission
(
    id   BIGINT       NOT NULL PRIMARY KEY DEFAULT nextval('permission_id_sequence'),
    name VARCHAR(255) NOT NULL UNIQUE
);

-- =============================================================
--         role table
-- =============================================================

DROP TABLE IF EXISTS role CASCADE;

CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255)          NOT NULL UNIQUE
);


-- =============================================================
--         role permission table
-- =============================================================
DROP TABLE IF EXISTS roles_permissions CASCADE;

CREATE TABLE IF NOT EXISTS roles_permissions
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


-- =============================================================
--         users table
-- =============================================================
CREATE SEQUENCE IF NOT EXISTS users_id_sequence
    START WITH 1
    INCREMENT BY 1;

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id                    BIGINT PRIMARY KEY    DEFAULT nextval('users_id_sequence'),
    user_uuid             VARCHAR(255) NOT NULL UNIQUE,

    email                 VARCHAR(255) NOT NULL UNIQUE,
    username              VARCHAR(255) UNIQUE,
    password              VARCHAR(255) NOT NULL,

    first_name            VARCHAR(255),
    last_name             VARCHAR(255),

    enable                BOOLEAN               DEFAULT TRUE,
    account_locked        BOOLEAN               DEFAULT FALSE,

    mfa                   BOOLEAN               DEFAULT FALSE,
    mfa_verified          BOOLEAN               DEFAULT FALSE,
    mfa_secret            VARCHAR(255),

    failed_login_attempts INTEGER               DEFAULT 0,
    last_login            TIMESTAMP,

    role_id               BIGINT
        CONSTRAINT fk_user_role REFERENCES role (id) ,

    -- Dates JPA Auditing
    created_at            TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP

);