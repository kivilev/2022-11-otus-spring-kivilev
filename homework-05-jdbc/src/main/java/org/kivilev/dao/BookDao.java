package org.kivilev.dao;

import org.kivilev.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getAllBooks();

    void save(Book book);

    void delete(long id);

    void updateTitle(long id, String title);

    Optional<Book> getBook(long id);
}
