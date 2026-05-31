package com.example.library.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    @Test
    void shouldCreateUserUsingFullConstructor() {
        User user = new User(7L, "Jan", "Kowalski", "jan.kowalski@example.com");

        assertEquals(7L, user.getId());
        assertEquals("Jan", user.getFirstName());
        assertEquals("Kowalski", user.getLastName());
        assertEquals("jan.kowalski@example.com", user.getEmail());
    }

    @Test
    void shouldUpdateUserUsingSetters() {
        User user = new User();
        user.setId(8L);
        user.setFirstName("Anna");
        user.setLastName("Nowak");
        user.setEmail("anna.nowak@example.com");

        assertEquals(8L, user.getId());
        assertEquals("Anna", user.getFirstName());
        assertEquals("Nowak", user.getLastName());
        assertEquals("anna.nowak@example.com", user.getEmail());
    }

    @Test
    void shouldFormatUserForConsoleOutput() {
        User user = new User(7L, "Jan", "Kowalski", "jan.kowalski@example.com");

        assertEquals("Uzytkownik #7: Jan Kowalski <jan.kowalski@example.com>", user.toString());
    }
}
