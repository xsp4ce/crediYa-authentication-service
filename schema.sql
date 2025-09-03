DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(100)   NOT NULL,
    last_name       VARCHAR(100)   NOT NULL,
    email           VARCHAR(100)   NOT NULL UNIQUE,
    password        VARCHAR(255)   NOT NULL,
    role_id         BIGINT         NOT NULL,
    base_salary     DECIMAL(12, 2) NOT NULL CHECK (base_salary > 0 AND base_salary <= 15000000),
    document_number VARCHAR(20),
    birth_date      DATE,
    address         VARCHAR(255),
    phone           VARCHAR(20),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_document_number ON users (document_number);
CREATE INDEX idx_users_role_id ON users (role_id);
CREATE INDEX idx_roles_name ON roles (name);

INSERT INTO roles (id, name, description) VALUES
(1, 'ADMINISTRATOR', 'Administrator with full access'),
(2, 'ADVISOR', 'Advisor with limited access'),
(3, 'CUSTOMER', 'Customer with access to own data');