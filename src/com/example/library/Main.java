package com.example.library;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        String query = "SELECT id, title, author, isbn FROM books;";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Lista książek:");

            while (rs.next()) {
                long id = rs.getLong("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");

                System.out.println("ID: " + id + " | Tytuł: " + title + " | Autor: " + author + " | ISBN: " + isbn);
            }

        } catch (SQLException e) {
            System.err.println("Wystąpił błąd podczas pobierania danych: " + e.getMessage());
        }
    }
}