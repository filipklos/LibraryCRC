package com.example.library.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookTest {
    @Test
    void shouldCreateBookUsingFullConstructor() {
        Book book = new Book(1L, "Dune", "Frank Herbert", "9780441172719");

        assertEquals(1L, book.getId());
        assertEquals("Dune", book.getTitle());
        assertEquals("Frank Herbert", book.getAuthor());
        assertEquals("9780441172719", book.getIsbn());
    }

    @Test
    void shouldUpdateBookUsingSetters() {
        Book book = new Book();
        book.setId(2L);
        book.setTitle("1984");
        book.setAuthor("George Orwell");
        book.setIsbn("9780451524935");

        assertEquals(2L, book.getId());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertEquals("9780451524935", book.getIsbn());
    }

    @Test
    void shouldFormatBookForConsoleOutput() {
        Book book = new Book(1L, "Dune", "Frank Herbert", "9780441172719");

        assertEquals("Ksiazka #1: Dune — Frank Herbert (ISBN: 9780441172719)", book.toString());
    }
}
