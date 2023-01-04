package org.kivilev.dao;

import org.kivilev.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> getAllBooks();

    void save(Book book);

    void delete(long bookId);

    void updateTitle(long bookId, String title);
}
