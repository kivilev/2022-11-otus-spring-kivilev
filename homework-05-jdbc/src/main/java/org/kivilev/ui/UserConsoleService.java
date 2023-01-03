package org.kivilev.ui;

import lombok.AllArgsConstructor;
import org.kivilev.service.AuthorPrintService;
import org.kivilev.service.AuthorService;
import org.kivilev.service.BookPrintService;
import org.kivilev.service.BookService;
import org.kivilev.service.GenrePrintService;
import org.kivilev.service.GenreService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.NotNull;

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
        //bookService.addBook();
    }

    @ShellMethod(value = "Удалить книгу", key = {"rb", "remove-book"})
    public void removeBook(@ShellOption(value = "-id", help = "ID книги", optOut = true) @NotNull Long bookId) {

    }

    @ShellMethod(value = "Изменить название книги", key = {"ubt", "update-book-title"})
    public void updateBookTitle() {

    }

    @ShellMethod(value = "Список книг", key = {"lb", "list-books"})
    public void showAllBooks() {
        bookPrintService.printBooks(bookService.getAllBooks());
    }

    @ShellMethod(value = "Список книг", key = {"la", "list-authors"})
    public void showAllAuthors() {
        authorPrintService.printAuthors(authorService.getAllAuthors());
    }

    @ShellMethod(value = "Список жанров", key = {"lg", "list-genres"})
    public void showAllGenres() {
        genrePrintService.printGenres(genreService.getAllGenres());
    }

}
