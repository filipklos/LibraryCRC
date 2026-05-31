package com.example.library.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ReservationTest {
    @Test
    void shouldCreateReservationWithBorrowDateOnly() {
        LocalDate borrowDate = LocalDate.of(2026, 5, 31);
        Reservation reservation = new Reservation(3L, 4L, borrowDate);

        assertNull(reservation.getId());
        assertEquals(3L, reservation.getUserId());
        assertEquals(4L, reservation.getBookId());
        assertEquals(borrowDate, reservation.getBorrowDate());
        assertNull(reservation.getReturnDate());
    }

    @Test
    void shouldCreateReservationUsingFullConstructor() {
        LocalDate borrowDate = LocalDate.of(2026, 5, 1);
        LocalDate returnDate = LocalDate.of(2026, 5, 15);
        Reservation reservation = new Reservation(9L, 3L, 4L, borrowDate, returnDate);

        assertEquals(9L, reservation.getId());
        assertEquals(3L, reservation.getUserId());
        assertEquals(4L, reservation.getBookId());
        assertEquals(borrowDate, reservation.getBorrowDate());
        assertEquals(returnDate, reservation.getReturnDate());
    }

    @Test
    void shouldFormatReservationForConsoleOutput() {
        Reservation reservation = new Reservation(9L, 3L, 4L, LocalDate.of(2026, 5, 1), null);

        assertEquals("Wypozyczenie #9: user=3, book=4, wypozyczenie=2026-05-01, zwrot=aktywnie wypozyczone", reservation.toString());
    }
}
