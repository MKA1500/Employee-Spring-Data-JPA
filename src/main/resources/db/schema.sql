CREATE DATABASE employee_project;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(100),
    phone_number VARCHAR(15)
);

-- for standard Spring Security would be:

-- CREATE TABLE users (
--    username VARCHAR(50) PRIMARY KEY,
--    password VARCHAR(68) NOT NULL,
--    enabled SMALLINT NOT NULL CHECK (enabled IN (0, 1))
-- );

CREATE TABLE members (
    user_id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(68) NOT NULL,
    active SMALLINT NOT NULL CHECK (active IN (0, 1))
);

-- for standard Spring Security would be:

-- CREATE TABLE roles (
--     username VARCHAR(50) NOT NULL,
--     authority VARCHAR(50) NOT NULL,
--     CONSTRAINT authorities_pk UNIQUE (username, authority),
--     CONSTRAINT authorities_fk FOREIGN KEY (username)
--         REFERENCES users (username) ON DELETE CASCADE
-- );

CREATE TABLE roles (
       user_id VARCHAR(50) NOT NULL,
       role VARCHAR(50) NOT NULL,
       CONSTRAINT authorities_pk UNIQUE (user_id, role),
       CONSTRAINT authorities_fk FOREIGN KEY (user_id)
           REFERENCES members (user_id) ON DELETE CASCADE
);

