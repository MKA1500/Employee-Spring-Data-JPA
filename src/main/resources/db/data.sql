-- BUSINESS DATA:

-- 1 employee

INSERT INTO employee (first_name, last_name, email, phone_number, employee_detail_id) VALUES
    ('Anna', 'Nowak', 'anna.nowak@gmail.com', '123-456-789', NULL),
    ('Piotr', 'Kowalski', 'piotr.kowalski@wp.pl', '234-567-890', NULL),
    ('Zofia', 'Wiśniewska', 'zofia.wisniewska@interia.pl', '345-678-901', NULL),
    ('Jan', 'Kamiński', 'jan.kaminski@onet.pl', '456-789-012', NULL),
    ('Maria', 'Lewandowska', 'maria.lewandowska@o2.pl', '567-890-123', NULL),
    ('Tomasz', 'Zieliński', 'tomasz.zielinski@gmail.com', '678-901-234', NULL),
    ('Ewa', 'Kaczmarek', 'ewa.kaczmarek@wp.pl', '789-012-345', NULL),
    ('Michał', 'Piotrowski', 'michal.piotrowski@interia.pl', '890-123-456', NULL),
    ('Katarzyna', 'Szymańska', 'katarzyna.szymanska@onet.pl', '901-234-567', NULL),
    ('Adam', 'Jankowski', 'adam.jankowski@o2.pl', '012-345-678', NULL);

-- 2 employee_detail

INSERT INTO employee_detail (department, rank, salary) VALUES
    ('Restaurant Staff', 1, 28000), -- Anna Nowak
    ('Restaurant Staff', 2, 32000), -- Piotr Kowalski
    ('Restaurant Staff', 3, 36000), -- Zofia Wiśniewska
    ('Management', 4, 50000),       -- Jan Kamiński
    ('Management', 4, 52000),       -- Maria Lewandowska
    ('Restaurant Staff', 1, 29000), -- Tomasz Zieliński
    ('Restaurant Staff', 2, 33000), -- Ewa Kaczmarek
    ('Kitchen Staff', 3, 37000),    -- Michał Piotrowski
    ('Kitchen Staff', 1, 30000),    -- Katarzyna Szymańska
    ('Delivery', 2, 31000);         -- Adam Jankowski

-- 3. update the employee_detail_id field in the employee table to establish the one-to-one relationship:

UPDATE employee SET employee_detail_id = ed.id
FROM employee_detail ed
WHERE employee.id = ed.id;

-- 4. Insert app users and roles - Spring Security

-- password for 3 users below is: test1234
-- encrypted with: https://www.bcryptcalculator.com/encode

INSERT INTO members (user_id, password, active) VALUES
    ('john', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1),
    ('jane', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1),
    ('maciej', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1);

INSERT INTO roles (user_id, role) VALUES
    ('john', 'ROLE_EMPLOYEE'),
    ('jane', 'ROLE_EMPLOYEE'),
    ('jane', 'ROLE_MANAGER'),
    ('maciej', 'ROLE_EMPLOYEE'),
    ('maciej', 'ROLE_MANAGER'),
    ('maciej', 'ROLE_ADMIN');