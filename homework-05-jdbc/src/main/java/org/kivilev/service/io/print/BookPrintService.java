package org.kivilev.service.io.print;

import org.kivilev.model.Book;

import java.util.List;

public interface BookPrintService {
    void printBooks(List<Book> allBooks);

    void printBook(Book book);
}
