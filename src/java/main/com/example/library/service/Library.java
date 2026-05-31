package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Reservation;
import com.example.library.model.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.ReservationRepository;
import com.example.library.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

public final class Library {
    private static volatile Library instance;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    private Library() {
        this.bookRepository = new BookRepository();
        this.userRepository = new UserRepository();
        this.reservationRepository = new ReservationRepository();
    }

    public static Library getInstance() {
        if (instance == null) {
            synchronized (Library.class) {
                if (instance == null) {
                    instance = new Library();
                }
            }
        }
        return instance;
    }

    public Book addBook(String title, String author, String isbn) {
        return bookRepository.add(new Book(title, author, isbn));
    }

    public boolean removeBook(long bookId) {
        return bookRepository.deleteById(bookId);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public User addUser(String firstName, String lastName, String email) {
        return userRepository.add(new User(firstName, lastName, email));
    }

    public boolean removeUser(long userId) {
        return userRepository.deleteById(userId);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Reservation borrowBook(long userId, long bookId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("Nie istnieje uzytkownik o id " + userId);
        }
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new IllegalArgumentException("Nie istnieje ksiazka o id " + bookId);
        }
        if (reservationRepository.findActiveByBookId(bookId).isPresent()) {
            throw new IllegalStateException("Ksiazka o id " + bookId + " jest juz wypozyczona");
        }
        return reservationRepository.add(new Reservation(userId, bookId, LocalDate.now()));
    }

    public boolean returnBook(long bookId) {
        return reservationRepository.returnBook(bookId, LocalDate.now());
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
