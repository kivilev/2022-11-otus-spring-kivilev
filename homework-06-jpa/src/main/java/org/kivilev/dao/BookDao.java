package org.kivilev.dao;

import org.kivilev.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> getAllBooks();

    Book save(Book book);

    void delete(Book book);

    Optional<Book> getBook(long id);
}
