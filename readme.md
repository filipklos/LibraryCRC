**System biblioteczny**

Opis projektu
Projekt to system do zarządzania biblioteką, umożliwiający ewidencję książek, użytkowników oraz procesów wypożyczeń. Został przygotowany jako projekt zaliczeniowy z wykorzystaniem języka Java i relacyjnej bazy danych SQL.

Funkcjonalności
* Zarządzanie użytkownikami: dodawanie i usuwanie osób korzystających z biblioteki.
* Zarządzanie księgozbiorem: dodawanie nowych książek do bazy oraz usuwanie istniejących pozycji.
Obsługa wypożyczeń: rejestrowanie wypożyczenia książki przez użytkownika oraz obsługa zwrotów.

Technologie
* Język programowania: Java
* Baza danych: SQL

Struktura bazy danych
System wykorzystuje trzy powiązane tabele:

users (użytkownicy)
* id
* imie
* nazwisko
* email

books (książki)
* id
* tytul 
* autor 
* isbn

reservations (wypożyczenia)
* id 
* user_id (klucz obcy do tabeli users)
* book_id (klucz obcy do tabeli books)
* data_wypozyczenia 
* data_zwrotu