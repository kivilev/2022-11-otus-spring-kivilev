package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.service.BookCommentService;
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
    private final BookCommentService bookCommentService;
    private final BookPrintService bookPrintService;
    private final InputBookService inputBookService;

    @Override
    public void showBook() {
        var bookId = inputBookService.getBookId();
        bookPrintService.print(bookService.getBook(bookId));
    }

    @Override
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
        var removeBookId = inputBookService.getRemoveId();
        bookService.removeBook(removeBookId);
    }

    @Override
    public void updateBookTitle() {
        Pair<String, String> updateData = inputBookService.getNewTitle();
        bookService.updateBookTitle(updateData);
    }

    @Override
    @Transactional(readOnly = true)
    public void showBookComments() {
        var bookId = inputBookService.getCommentsBookId();
        var book = bookService.getBook(bookId);
        var allComments = bookCommentService.getAllComments(bookId);
        bookPrintService.printCommentsWithTop(book.getLastTopComments(), allComments);
    }
}
