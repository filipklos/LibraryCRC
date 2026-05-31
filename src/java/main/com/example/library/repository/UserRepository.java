package com.example.library.repository;

import com.example.library.config.DatabaseManager;
import com.example.library.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    public User add(User user) {
        String sql = "INSERT INTO users(imie, nazwisko, email) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie dodac uzytkownika", e);
        }
    }

    public boolean deleteById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie usunac uzytkownika", e);
        }
    }

    public Optional<User> findById(long id) {
        String sql = "SELECT id, imie, nazwisko, email FROM users WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getLong("id"),
                            rs.getString("imie"),
                            rs.getString("nazwisko"),
                            rs.getString("email")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac uzytkownika", e);
        }
    }

    public List<User> findAll() {
        String sql = "SELECT id, imie, nazwisko, email FROM users ORDER BY id";
        List<User> users = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("imie"),
                        rs.getString("nazwisko"),
                        rs.getString("email")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Nie udalo sie pobrac listy uzytkownikow", e);
        }
    }
}
