package com.example.library.model;

import java.time.LocalDate;

public class Reservation {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public Reservation() {
    }

    public Reservation(Long id, Long userId, Long bookId, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Reservation(Long userId, Long bookId, LocalDate borrowDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        String idPart = id == null ? "nowe" : String.valueOf(id);
        String returnPart = returnDate == null ? "aktywnie wypozyczone" : returnDate.toString();
        return "Wypozyczenie #" + idPart + ": user=" + userId + ", book=" + bookId + ", wypozyczenie=" + borrowDate + ", zwrot=" + returnPart;
    }
}
