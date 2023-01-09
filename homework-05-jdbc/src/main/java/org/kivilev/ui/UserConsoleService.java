package org.kivilev.ui;

import lombok.AllArgsConstructor;
import org.kivilev.service.io.print.AuthorPrintService;
import org.kivilev.service.AuthorService;
import org.kivilev.service.io.print.BookPrintService;
import org.kivilev.service.BookService;
import org.kivilev.service.io.print.GenrePrintService;
import org.kivilev.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserConsoleService {

    private final BookService bookService;
    private final BookPrintService bookPrintService;
    private final GenreService genreService;
    private final GenrePrintService genrePrintService;
    private final AuthorService authorService;
    private final AuthorPrintService authorPrintService;

    @ShellMethod(value = "Добавить книгу", key = {"ab", "add-book"})
    public void addBook() {
        bookService.addBook();
    }

    @ShellMethod(value = "Удалить книгу", key = {"rb", "remove-book"})
    public void removeBook() {
        bookService.removeBook();
    }

    @ShellMethod(value = "Изменить название книги", key = {"ubt", "update-book-title"})
    public void updateBookTitle() {
        bookService.updateBookTitle();
    }

    @ShellMethod(value = "Список книг", key = {"lb", "list-books"})
    public void showAllBooks() {
        bookPrintService.printBooks(bookService.getAllBooks());
    }

    @ShellMethod(value = "Список авторов", key = {"la", "list-authors"})
    public void showAllAuthors() {
        authorPrintService.print(authorService.getAllAuthors());
    }

    @ShellMethod(value = "Список жанров", key = {"lg", "list-genres"})
    public void showAllGenres() {
        genrePrintService.print(genreService.getAllGenres());
    }

}
