package com.example.library.service;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertSame;

class LibrarySingletonTest {
    @Test
    void shouldReturnTheSameInstance() {
        Library first = Library.getInstance();
        Library second = Library.getInstance();

        assertSame(first, second);
    }

    @Test
    void shouldReturnOneInstanceForConcurrentCalls() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        List<Callable<Library>> tasks = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            tasks.add(Library::getInstance);
        }

        List<Future<Library>> futures = executorService.invokeAll(tasks);
        executorService.shutdown();

        Library expected = futures.get(0).get();
        for (Future<Library> future : futures) {
            assertSame(expected, future.get());
        }
    }
}
