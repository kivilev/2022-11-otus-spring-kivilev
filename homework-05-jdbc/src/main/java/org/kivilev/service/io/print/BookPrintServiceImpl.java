package org.kivilev.service.io.print;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Book;
import org.kivilev.service.io.OutputStreamData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPrintServiceImpl implements BookPrintService {
    private final OutputStreamData outputStreamData;
    private final AuthorPrintService authorPrintService;
    private final GenrePrintService genrePrintService;

    @Override
    public void printBooks(List<Book> allBooks) {
        allBooks.forEach(this::printBook);
    }

    @Override
    public void printBook(Book book) {
        outputStreamData.format("[%d] Title: %s. Created: %s\n", book.getId(), book.getTitle(), book.getCreatedYear());

        outputStreamData.print("\tAuthor: ");
        authorPrintService.print(book.getAuthor());

        outputStreamData.print("\tGenre: ");
        genrePrintService.print(book.getGenre());
        outputStreamData.print("\n");
    }
}
