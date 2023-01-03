package org.kivilev.service;

import org.kivilev.model.Book;

import java.util.List;

public interface BookPrintService {
    void printBooks(List<Book> allBooks);
}
