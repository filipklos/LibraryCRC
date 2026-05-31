DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    imie     VARCHAR(100) NOT NULL,
    nazwisko VARCHAR(100) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE books
(
    id     SERIAL PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn   VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE reservations
(
    id                SERIAL PRIMARY KEY,
    user_id           INT  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    book_id           INT  NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    data_wypozyczenia DATE NOT NULL,
    data_zwrotu       DATE
);

CREATE UNIQUE INDEX IF NOT EXISTS reservations_active_book_unique
    ON reservations (book_id)
    WHERE data_zwrotu IS NULL;

INSERT INTO users (imie, nazwisko, email) VALUES
('Jan', 'Kowalski', 'jan.kowalski@example.com'),
('Anna', 'Nowak', 'anna.nowak@example.com'),
('Piotr', 'Wisniewski', 'piotr.wisniewski@example.com');

INSERT INTO books (title, author, isbn) VALUES
('Dune', 'Frank Herbert', '9780441172719'),
('1984', 'George Orwell', '9780451524935'),
('Hobbit', 'J.R.R. Tolkien', '9780547928227');

INSERT INTO reservations (user_id, book_id, data_wypozyczenia, data_zwrotu) VALUES
(1, 1, DATE '2025-01-10', NULL),
(2, 2, DATE '2025-01-12', DATE '2025-01-20'),
(3, 3, DATE '2025-01-15', NULL);
