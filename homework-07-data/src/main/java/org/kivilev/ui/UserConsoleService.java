package org.kivilev.ui;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

@ShellComponent
@AllArgsConstructor
public class UserConsoleService {

    private final GenreUiService genreUiService;
    private final AuthorUiService authorUiService;
    private final BookUiService bookUiService;
    private final BookCommentUiService bookCommentUiService;

    @ShellMethod(value = "Добавить книгу", key = {"ab", "add-book"})
    public void addBook() {
        bookUiService.addBook();
    }

    @ShellMethod(value = "Удалить книгу", key = {"rb", "remove-book"})
    public void removeBook() {
        bookUiService.removeBook();
    }

    @ShellMethod(value = "Изменить название книги", key = {"ubt", "update-book-title"})
    public void updateBookTitle() {
        bookUiService.updateBookTitle();
    }

    @ShellMethod(value = "Список книг", key = {"lb", "list-books"})
    @Transactional
    public void showAllBooks() {
        bookUiService.showAllBooks();
    }

    @ShellMethod(value = "Информация по одной книге", key = {"ob", "one-book"})
    @Transactional
    public void showBook() {
        bookUiService.showBook();
    }

    @ShellMethod(value = "Список авторов", key = {"la", "list-authors"})
    public void showAllAuthors() {
        authorUiService.showAllAuthors();
    }

    @ShellMethod(value = "Список жанров", key = {"lg", "list-genres"})
    public void showAllGenres() {
        genreUiService.showAllGenres();
    }

    @ShellMethod(value = "Добавить комментарий к книге", key = {"ac", "add-comment"})
    public void addBookComment() {
        bookCommentUiService.addBookComment();
    }

    @ShellMethod(value = "Удалить комментарий к книге", key = {"rc", "remove-comment"})
    public void removeBookComment() {
        bookCommentUiService.removeBookComment();
    }

    @ShellMethod(value = "Список комментариев к книге", key = {"lc", "list-comments"})
    public void showBookComment() {
        bookUiService.showBookComments();
    }
}
