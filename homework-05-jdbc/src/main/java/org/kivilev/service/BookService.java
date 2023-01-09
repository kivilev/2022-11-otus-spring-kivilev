package org.kivilev.service;

import org.kivilev.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    void addBook();

    void removeBook();

    void updateBookTitle();
}
