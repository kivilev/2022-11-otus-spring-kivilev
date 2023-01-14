package org.kivilev.service;

import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.model.Book;
import org.kivilev.model.BookFields;

import java.util.List;
import java.util.Map;

public interface BookService {
    List<Book> getAllBooks();

    Book addBook(Map<BookFields, Object> bookFields);

    void removeBook(long removeBookId);

    void updateBookTitle(Pair<Long, String> updateData);

    Book getBook(long id);
}
