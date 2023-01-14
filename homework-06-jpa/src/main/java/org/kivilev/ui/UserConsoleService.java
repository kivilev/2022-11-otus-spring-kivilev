package org.kivilev.ui;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.model.BookFields;
import org.kivilev.service.AuthorService;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.BookService;
import org.kivilev.service.GenreService;
import org.kivilev.service.io.input.InputBookCommentService;
import org.kivilev.service.io.input.InputBookService;
import org.kivilev.service.io.print.AuthorPrintService;
import org.kivilev.service.io.print.BookPrintService;
import org.kivilev.service.io.print.GenrePrintService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@ShellComponent
@AllArgsConstructor
public class UserConsoleService {

    private final BookService bookService;
    private final BookPrintService bookPrintService;
    private final GenreService genreService;
    private final GenrePrintService genrePrintService;
    private final AuthorService authorService;
    private final AuthorPrintService authorPrintService;
    private final BookCommentService bookCommentService;
    private final InputBookService inputBookService;
    private final InputBookCommentService inputBookCommentService;

    @ShellMethod(value = "Добавить книгу", key = {"ab", "add-book"})
    public void addBook() {
        Map<BookFields, Object> bookFields = inputBookService.getNewBook();
        bookService.addBook(bookFields);
    }

    @ShellMethod(value = "Удалить книгу", key = {"rb", "remove-book"})
    public void removeBook() {
        long removeBookId = inputBookService.getRemoveId();
        bookService.removeBook(removeBookId);
    }

    @ShellMethod(value = "Изменить название книги", key = {"ubt", "update-book-title"})
    public void updateBookTitle() {
        Pair<Long, String> updateData = inputBookService.getNewTitle();
        bookService.updateBookTitle(updateData);
    }

    @ShellMethod(value = "Список книг", key = {"lb", "list-books"})
    @Transactional
    public void showAllBooks() {
        bookPrintService.print(bookService.getAllBooks());
    }

    @ShellMethod(value = "Информация по одной книге", key = {"ob", "one-book"})
    @Transactional
    public void showBook() {
        long bookId = inputBookService.getBookId();
        bookPrintService.print(bookService.getBook(bookId));
    }

    @ShellMethod(value = "Список авторов", key = {"la", "list-authors"})
    public void showAllAuthors() {
        authorPrintService.print(authorService.getAllAuthors());
    }

    @ShellMethod(value = "Список жанров", key = {"lg", "list-genres"})
    public void showAllGenres() {
        genrePrintService.print(genreService.getAllGenres());
    }

    @ShellMethod(value = "Добавить комментарий к книге", key = {"ac", "add-comment"})
    public void addBookComment() {
        var newData = inputBookCommentService.getNewComment();
        bookCommentService.addComment(newData);
    }

    @ShellMethod(value = "Удалить комментарий к книге", key = {"rc", "remove-comment"})
    public void removeBookComment() {
        var removeId = inputBookCommentService.getRemoveId();
        bookCommentService.removeComment(removeId);
    }
}
