CREATE DATABASE employee_project;

-- DROP TABLE IF EXISTS employee_detail;

CREATE TABLE employee_detail (
    id SERIAL PRIMARY KEY,
    department VARCHAR(45),
    rank INT,
    salary BIGINT
);

-- DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(100),
    phone_number VARCHAR(15),
    employee_detail_id INT UNIQUE REFERENCES employee_detail(id)
);

-- DROP TABLE IF EXISTS employee_benefit;

CREATE TABLE employee_benefit (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45),
    cost BIGINT,
    employee_id INT REFERENCES employee(id)
);

-- for standard Spring Security would be:

-- CREATE TABLE users (
--    username VARCHAR(50) PRIMARY KEY,
--    password VARCHAR(68) NOT NULL,
--    enabled SMALLINT NOT NULL CHECK (enabled IN (0, 1))
-- );

-- DROP TABLE IF EXISTS members;

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

-- DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
       user_id VARCHAR(50) NOT NULL,
       role VARCHAR(50) NOT NULL,
       CONSTRAINT authorities_pk UNIQUE (user_id, role),
       CONSTRAINT authorities_fk FOREIGN KEY (user_id)
           REFERENCES members (user_id) ON DELETE CASCADE
);

