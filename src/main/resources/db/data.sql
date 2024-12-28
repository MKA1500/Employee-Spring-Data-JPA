INSERT INTO employee (first_name, last_name, email, phone_number) VALUES
    ('Anna', 'Nowak', 'anna.nowak@gmail.com', '123-456-789'),
    ('Piotr', 'Kowalski', 'piotr.kowalski@wp.pl', '234-567-890'),
    ('Zofia', 'Wiśniewska', 'zofia.wisniewska@interia.pl', '345-678-901'),
    ('Jan', 'Kamiński', 'jan.kaminski@onet.pl', '456-789-012'),
    ('Maria', 'Lewandowska', 'maria.lewandowska@o2.pl', '567-890-123');

-- password for 3 users below is: test1234
-- encrypted with: https://www.bcryptcalculator.com/encode

INSERT INTO users (username, password, enabled) VALUES
    ('john', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1),
    ('jane', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1),
    ('maciej', '{bcrypt}$2a$10$WPYI0ymGl77kScZXmsVFleTDd4EeI.9ZzCp7yR.rc53m.TLR12vkO', 1);

INSERT INTO authorities (username, authority) VALUES
    ('john', 'ROLE_EMPLOYEE'),
    ('jane', 'ROLE_EMPLOYEE'),
    ('jane', 'ROLE_MANAGER'),
    ('maciej', 'ROLE_EMPLOYEE'),
    ('maciej', 'ROLE_MANAGER'),
    ('maciej', 'ROLE_ADMIN');