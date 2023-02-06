package org.kivilev.service.io.print;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Book;
import org.kivilev.model.BookComment;
import org.kivilev.service.io.OutputStreamData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPrintServiceImpl implements BookPrintService {
    private final OutputStreamData outputStreamData;
    private final AuthorPrintService authorPrintService;
    private final GenrePrintService genrePrintService;
    private final BookCommentPrintService bookCommentPrintService;

    @Override
    public void print(List<Book> allBooks) {
        allBooks.forEach(this::print);
    }

    @Override
    public void print(Book book) {
        outputStreamData.format("[%s] Title: %s. Created: %s\n", book.getId(), book.getTitle(), book.getCreatedYear());

        outputStreamData.print("~~ Author: ");
        authorPrintService.print(book.getAuthor());

        outputStreamData.print("~~ Genre: ");
        genrePrintService.print(book.getGenre());

        outputStreamData.print("~~ Last N comments: \n");
        printComments(book.getLastTopComments());
        outputStreamData.print("\n");
    }

    @Override
    public void printComments(List<BookComment> bookComments) {
        bookCommentPrintService.print(bookComments);
    }

    @Override
    public void printCommentsWithTop(List<BookComment> topComments, List<BookComment> allComments) {
        outputStreamData.print("~~ Last N comments: \n");
        printComments(topComments);
        outputStreamData.print("~~ All comments: \n");
        printComments(allComments);
    }
}
