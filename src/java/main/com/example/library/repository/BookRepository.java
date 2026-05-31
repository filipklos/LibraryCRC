package com.example.library.repository;

import com.example.library.config.DatabaseManager;
import com.example.library.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository {
    public Book add(Book book) {
        String sql = "INSERT INTO books(title, author, isbn) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setString(3, book.getIsbn());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    book.setId(keys.getLong(1));
                }
            }
            return book;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie dodac ksiazki", e);
        }
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie usunac ksiazki", e);
        }
    }

    public Optional<Book> findById(long id) {
        String sql = "SELECT id, title, author, isbn FROM books WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac ksiazki", e);
        }
    }

    public List<Book> findAll() {
        String sql = "SELECT id, title, author, isbn FROM books ORDER BY id";
        List<Book> books = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn")
                ));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac listy ksiazek", e);
        }
    }
}
