package com.example.library.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5024/library_db";
    private static final String USER = "postgres";

    private DatabaseManager() {
    }

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("POSTGRES_PASSWORD");
        if (password == null || password.isBlank()) {
            password = System.getProperty("POSTGRES_PASSWORD");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalStateException("Brak POSTGRES_PASSWORD. Ustaw zmienna srodowiskowa albo uruchom program z -DPOSTGRES_PASSWORD=...");
        }
        return DriverManager.getConnection(URL, USER, password);
    }
}