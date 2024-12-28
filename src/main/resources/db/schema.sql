CREATE DATABASE employee_project;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(100),
    phone_number VARCHAR(15)
);

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(68) NOT NULL,
    enabled SMALLINT NOT NULL CHECK (enabled IN (0, 1))
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_pk UNIQUE (username, authority),
    CONSTRAINT authorities_fk FOREIGN KEY (username)
        REFERENCES users (username) ON DELETE CASCADE
);

