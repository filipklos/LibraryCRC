package com.example.library.app;

import com.example.library.model.Book;
import com.example.library.model.Reservation;
import com.example.library.model.User;
import com.example.library.service.Library;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = Library.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addUser(library, scanner);
                    case "2" -> removeUser(library, scanner);
                    case "3" -> addBook(library, scanner);
                    case "4" -> removeBook(library, scanner);
                    case "5" -> borrowBook(library, scanner);
                    case "6" -> returnBook(library, scanner);
                    case "7" -> printUsers(library.getUsers());
                    case "8" -> printBooks(library.getBooks());
                    case "9" -> printReservations(library.getReservations());
                    case "0" -> running = false;
                    default -> System.out.println("Nieznana opcja");
                }
            } catch (RuntimeException e) {
                System.out.println("Blad: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== SYSTEM BIBLIOTECZNY ===");
        System.out.println("1. Dodaj uzytkownika");
        System.out.println("2. Usun uzytkownika");
        System.out.println("3. Dodaj ksiazke");
        System.out.println("4. Usun ksiazke");
        System.out.println("5. Wypozycz ksiazke");
        System.out.println("6. Zwroc ksiazke");
        System.out.println("7. Lista uzytkownikow");
        System.out.println("8. Lista ksiazek");
        System.out.println("9. Lista wypozyczen");
        System.out.println("0. Wyjscie");
        System.out.print("Wybierz opcje: ");
    }

    private static void addUser(Library library, Scanner scanner) {
        System.out.print("Imie: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Nazwisko: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        User user = library.addUser(firstName, lastName, email);
        System.out.println("Dodano: " + user);
    }

    private static void removeUser(Library library, Scanner scanner) {
        System.out.print("ID uzytkownika: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        boolean removed = library.removeUser(id);
        System.out.println(removed ? "Usunieto uzytkownika" : "Nie znaleziono uzytkownika");
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.print("Tytul: ");
        String title = scanner.nextLine().trim();
        System.out.print("Autor: ");
        String author = scanner.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        Book book = library.addBook(title, author, isbn);
        System.out.println("Dodano: " + book);
    }

    private static void removeBook(Library library, Scanner scanner) {
        System.out.print("ID ksiazki: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        boolean removed = library.removeBook(id);
        System.out.println(removed ? "Usunieto ksiazke" : "Nie znaleziono ksiazki");
    }

    private static void borrowBook(Library library, Scanner scanner) {
        System.out.print("ID uzytkownika: ");
        long userId = Long.parseLong(scanner.nextLine().trim());
        System.out.print("ID ksiazki: ");
        long bookId = Long.parseLong(scanner.nextLine().trim());
        Reservation reservation = library.borrowBook(userId, bookId);
        System.out.println("Wypozyczono: " + reservation);
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.print("ID ksiazki: ");
        long bookId = Long.parseLong(scanner.nextLine().trim());
        boolean returned = library.returnBook(bookId);
        System.out.println(returned ? "Zwrot zarejestrowany" : "Brak aktywnego wypozyczenia tej ksiazki");
    }

    private static void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Brak uzytkownikow");
            return;
        }
        System.out.println("ID | IMIE | NAZWISKO | EMAIL");
        users.forEach(user -> System.out.println(row(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail())));
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Brak ksiazek");
            return;
        }
        System.out.println("ID | TYTUL | AUTOR | ISBN");
        books.forEach(book -> System.out.println(row(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn())));
    }

    private static void printReservations(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            System.out.println("Brak wypozyczen");
            return;
        }
        System.out.println("ID | USER_ID | BOOK_ID | WYPOZYCZENIE | ZWROT");
        reservations.forEach(reservation -> System.out.println(row(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getBookId(),
                reservation.getBorrowDate(),
                reservation.getReturnDate() == null ? "-" : reservation.getReturnDate()
        )));
    }

    private static String row(Object... values) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i > 0) {
                builder.append(" | ");
            }
            builder.append(values[i] == null ? "-" : values[i]);
        }
        return builder.toString();
    }
}