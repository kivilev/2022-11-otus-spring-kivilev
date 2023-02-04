package org.kivilev.service.io.print;

import org.kivilev.model.Book;
import org.kivilev.model.BookComment;

import java.util.List;

public interface BookPrintService {
    void print(List<Book> allBooks);

    void print(Book book);

    void printComments(List<BookComment> bookComments);
    void printCommentsWithTop(List<BookComment> topComments, List<BookComment> allComments);
}
