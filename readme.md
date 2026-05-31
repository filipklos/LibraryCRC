# System biblioteczny

## Opis projektu
Projekt to system do zarządzania biblioteką (ewidencja książek, użytkowników i wypożyczeń) przygotowany jako projekt zaliczeniowy w języku Java 17. Komunikacja z bazą danych PostgreSQL (uruchamiana w Dockerze) realizowana jest przez JDBC i Maven.

---

## Funkcjonalności
* **Zarządzanie użytkownikami:** dodawanie i usuwanie osób.
* **Zarządzanie księgozbiorem:** dodawanie i usuwanie książek.
* **Obsługa wypożyczeń:** rejestrowanie wypożyczeń oraz zwrotów.
* **Jedna instancja biblioteki w aplikacji przez wzorzec Singleton** (`Library.getInstance()`).

---

## Technologie i narzędzia
* **Język programowania:** Java 17 (JVM target 17)
* **Zarządzanie projektem:** Maven
* **Baza danych:** PostgreSQL 18 (Docker)
* **Biblioteka połączenia:** JDBC (PostgreSQL Driver 42.7.3)

---

## Struktura projektu
* `src/java/main/com/example/library/app` - punkt startowy aplikacji
* `src/java/main/com/example/library/config` - konfiguracja połączenia z bazą
* `src/java/main/com/example/library/model` - modele domenowe
* `src/java/main/com/example/library/repository` - operacje JDBC
* `src/java/main/com/example/library/service` - logika biznesowa i Singleton biblioteki
* `src/java/test/com/example/library/model` - testy modeli
* `src/java/test/com/example/library/service` - testy serwisu

---

## Uruchomienie środowiska

### 1. Start bazy danych (Docker)
Serwer Postgres działa w kontenerze, a na maszynie lokalnej jest wystawiony na porcie `5024`.

Plik `.env` w katalogu głównym jest używany przez `docker compose` do podstawienia hasła w `docker-compose.yml`.

Jeśli `.env` istnieje i zawiera `POSTGRES_PASSWORD`, po prostu uruchom:
```bash
docker compose up -d
```

Jeśli nie masz pliku `.env`, ustaw hasło przed startem:
```bash
export POSTGRES_PASSWORD=YourPasswordHere
docker compose up -d
```

### 2. Hasło dla aplikacji Java
Aplikacja czyta `POSTGRES_PASSWORD` w tej kolejności:
1. zmienna środowiskowa `POSTGRES_PASSWORD`,
2. parametr JVM `-DPOSTGRES_PASSWORD=...` podany przy uruchamianiu programu.

Jeśli nie chcesz używać pliku `.env`, możesz uruchomić aplikację bezpośrednio z hasłem podanym w terminalu:
```bash
POSTGRES_PASSWORD=YourPasswordHere mvn -q exec:java -Dexec.mainClass=com.example.library.app.Main
```

Jeśli uruchamiasz z terminala, możesz zrobić na przykład:
```bash
POSTGRES_PASSWORD=YourPasswordHere mvn -q exec:java -Dexec.mainClass=com.example.library.app.Main
```

Jeśli uruchamiasz w IntelliJ, dodaj `-DPOSTGRES_PASSWORD=YourPasswordHere` w polu **VM options** konfiguracji uruchomieniowej.

### 3. Inicjalizacja schematu i danych przykładowych
Skrypt `database.sql` robi pełny reset bazy: usuwa schemat `public`, tworzy tabele od nowa i dodaje dane przykładowe.

Ponieważ w kontenerze nie musisz mieć lokalnego `psql`, najprościej wykonać import tak:
```bash
docker compose exec -T db psql -U postgres -d library_db < database.sql
```

Jeśli używasz lokalnego klienta `psql`, ta sama komenda działa też z hosta:
```bash
psql -h localhost -p 5024 -U postgres -d library_db -f database.sql
```

### 4. Kompilacja i test
Kompilacja projektu oraz uruchomienie testów jednostkowych:
```bash
mvn clean test
```

### 5. Start aplikacji
Uruchomienie aplikacji:
```bash
mvn -q exec:java -Dexec.mainClass=com.example.library.app.Main
```

---

## Struktura bazy danych
Struktura tabel jest zarządzana bezpośrednio z poziomu `database.sql` w bazie `library_db`.

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