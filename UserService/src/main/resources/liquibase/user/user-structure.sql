CREATE TABLE roles (
    role_id SHORT NOT NULL PRIMARY KEY,
    role_name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    login VARCHAR(20) NOT NULL UNIQUE,
    role_id SHORT NOT NULL,
    password VARCHAR(255) NOT NULL,

    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);