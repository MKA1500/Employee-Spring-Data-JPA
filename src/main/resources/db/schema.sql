CREATE DATABASE employee_project;

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(100),
    phone_number VARCHAR(15)
);