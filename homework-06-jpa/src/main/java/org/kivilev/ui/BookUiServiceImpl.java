package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.service.BookService;
import org.kivilev.service.io.input.InputBookService;
import org.kivilev.service.io.print.BookPrintService;
import org.kivilev.ui.model.BookFields;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class BookUiServiceImpl implements BookUiService {

    private final BookService bookService;
    private final BookPrintService bookPrintService;
    private final InputBookService inputBookService;

    @Override
    @Transactional(readOnly = true)
    public void showBook() {
        long bookId = inputBookService.getBookId();
        bookPrintService.print(bookService.getBook(bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public void showAllBooks() {
        bookPrintService.print(bookService.getAllBooks());
    }

    @Override
    public void addBook() {
        Map<BookFields, Object> bookFields = inputBookService.getNewBook();
        bookService.addBook(bookFields);
    }

    @Override
    public void removeBook() {
        long removeBookId = inputBookService.getRemoveId();
        bookService.removeBook(removeBookId);
    }

    @Override
    public void updateBookTitle() {
        Pair<Long, String> updateData = inputBookService.getNewTitle();
        bookService.updateBookTitle(updateData);
    }

    @Override
    @Transactional(readOnly = true)
    public void showBookComments() {
        long bookId = inputBookService.getCommentsBookId();
        var book = bookService.getBook(bookId);
        bookPrintService.printComments(book.getComments());
    }
}
