package com.example.library.repository;

import com.example.library.config.DatabaseManager;
import com.example.library.model.Reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationRepository {
    public Reservation add(Reservation reservation) {
        String sql = "INSERT INTO reservations(user_id, book_id, data_wypozyczenia, data_zwrotu) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, reservation.getUserId());
            statement.setLong(2, reservation.getBookId());
            statement.setDate(3, Date.valueOf(reservation.getBorrowDate()));
            if (reservation.getReturnDate() == null) {
                statement.setDate(4, null);
            } else {
                statement.setDate(4, Date.valueOf(reservation.getReturnDate()));
            }
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    reservation.setId(keys.getLong(1));
                }
            }
            return reservation;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie dodac wypozyczenia", e);
        }
    }

    public Optional<Reservation> findActiveByBookId(long bookId) {
        String sql = "SELECT id, user_id, book_id, data_wypozyczenia, data_zwrotu FROM reservations WHERE book_id = ? AND data_zwrotu IS NULL ORDER BY id DESC LIMIT 1";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, bookId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapReservation(rs));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac aktywnego wypozyczenia", e);
        }
    }

    public boolean returnBook(long bookId, LocalDate returnDate) {
        String sql = "UPDATE reservations SET data_zwrotu = ? WHERE book_id = ? AND data_zwrotu IS NULL";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(returnDate));
            statement.setLong(2, bookId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie zarejestrowac zwrotu", e);
        }
    }

    public List<Reservation> findAll() {
        String sql = "SELECT id, user_id, book_id, data_wypozyczenia, data_zwrotu FROM reservations ORDER BY id";
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                reservations.add(mapReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac listy wypozyczen", e);
        }
    }

    private Reservation mapReservation(ResultSet rs) throws SQLException {
        Date returnDate = rs.getDate("data_zwrotu");
        return new Reservation(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("book_id"),
                rs.getDate("data_wypozyczenia").toLocalDate(),
                returnDate == null ? null : returnDate.toLocalDate()
        );
    }
}
