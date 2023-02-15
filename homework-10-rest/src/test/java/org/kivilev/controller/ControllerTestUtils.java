/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.BookComment;
import org.kivilev.model.Genre;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ControllerTestUtils {

    public static Book createDefaultBook(Long bookId) {
        return new Book(bookId, UUID.randomUUID().toString(), 1111,
                new Author(1L, UUID.randomUUID().toString(), LocalDate.now(), null),
                new Genre(1L, UUID.randomUUID().toString()),
                List.of(new BookComment(1L, UUID.randomUUID().toString(), LocalDateTime.now()),
                        new BookComment(2L, UUID.randomUUID().toString(), LocalDateTime.now()))
        );
    }
}
