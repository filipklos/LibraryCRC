# System biblioteczny

## Opis projektu
Projekt to system do zarządzania biblioteką (ewidencja książek, użytkowników i wypożyczeń) przygotowany jako projekt zaliczeniowy w języku Java 17. Komunikacja z bazą danych PostgreSQL (uruchamianą w Dockerze) realizowana jest za pomocą czystego mechanizmu JDBC za pośrednictwem narzędzia Maven.

---

## Funkcjonalności
* **Zarządzanie użytkownikami:** dodawanie i usuwanie osób.
* **Zarządzanie księgozbiorem:** dodawanie i usuwanie książek.
* **Obsługa wypożyczeń:** rejestrowanie wypożyczeń oraz zwrotów.

---

## Technologie i narzędzia
* **Język programowania:** Java 17 (JVM target 17)
* **Zarządzanie projektem:** Maven
* **Baza danych:** PostgreSQL 18 (Docker)
* **Zarządzanie bazą danych:** DataGrip
* **Biblioteka połączenia:** JDBC (PostgreSQL Driver 42.7.3)

---

## Uruchomienie środowiska

### 1. Baza danych (Docker)
Serwer Postgres działa w kontenerze, a na maszynie lokalnej jest wystawiony na porcie `5024`. Hasło przekazywane jest bezpiecznie przez zmienną środowiskową `POSTGRES_PASSWORD`.

Uruchomienie kontenera:
```bash
export POSTGRES_PASSWORD=YourPasswordHere
docker compose up -d
```

### 2. Aplikacja (Java)
Klasa `DatabaseManager` dynamicznie pobiera poświadczenia z konfiguracji uruchomieniowej systemu za pomocą `System.getenv("POSTGRES_PASSWORD")`. Eliminuje to przechowywanie haseł bezpośrednio w kodzie źródłowym.
* **URL:** `jdbc:postgresql://localhost:5024/library_db`
* **Użytkownik:** `postgres`

---

## Struktura bazy danych
Struktura tabel jest zarządzana bezpośrednio z poziomu DataGripa w bazie `library_db`:

### users (użytkownicy)
* `id` (SERIAL, PRIMARY KEY)
* `imie` (VARCHAR), `nazwisko` (VARCHAR), `email` (VARCHAR)

### books (książki)
* `id` (SERIAL, PRIMARY KEY)
* `title` (VARCHAR), `author` (VARCHAR), `isbn` (VARCHAR)

### reservations (wypożyczenia)
* `id` (SERIAL, PRIMARY KEY)
* `user_id` (INT, FOREIGN KEY do users)
* `book_id` (INT, FOREIGN KEY do books)
* `data_wypozyczenia` (DATE), `data_zwrotu` (DATE)