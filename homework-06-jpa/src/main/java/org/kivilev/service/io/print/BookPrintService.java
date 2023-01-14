package org.kivilev.service.io.print;

import org.kivilev.model.Book;

import java.util.List;

public interface BookPrintService {
    void print(List<Book> allBooks);

    void print(Book book);
}
