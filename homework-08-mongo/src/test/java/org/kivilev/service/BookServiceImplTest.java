package org.kivilev.service;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Author;
import org.kivilev.model.Genre;
import org.kivilev.ui.model.BookFields;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@SpringBootTest
class BookServiceImplTest {

    private static final Integer CREATED_YEAR = 2020;
    private static final String TITLE = "Название";
    private static final String AUTHOR_ID = "1";
    private static final String GENRE_ID = "2";
    private static final String GENRE_NAME = "Жанр";
    private static final String AUTHOR_NAME = "Имя автора";
    private static final LocalDate BIRTHDAY = LocalDate.of(1900, 1, 2);
    private static final LocalDate DEATHDAY = LocalDate.of(2000, 3, 4);
    private static final String NOT_EXISTED_BOOK_ID = "111";
    private static final Map<BookFields, Object> CORRECT_BOOK_FIELDS = Map.of(
            BookFields.TITLE, TITLE,
            BookFields.CREATED_YEAR, CREATED_YEAR,
            BookFields.AUTHOR_ID, AUTHOR_ID,
            BookFields.GENRE_ID, GENRE_ID
    );

    @Autowired
    private BookService bookService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private BookRepository bookRepository;

    @Test
    @DisplayName("Создание книги с корректными параметрами происходит успешно")
    public void addingBookWithCorrectDataShouldBeSuccessful() {
        Mockito.when(genreService.getGenre(GENRE_ID)).thenReturn(new Genre(GENRE_ID, GENRE_NAME));
        Mockito.when(authorService.getAuthor(AUTHOR_ID)).thenReturn(new Author(AUTHOR_ID, AUTHOR_NAME, BIRTHDAY, DEATHDAY));

        bookService.addBook(CORRECT_BOOK_FIELDS);

        Mockito.verify(bookRepository, times(1)).save(argThat(
                book -> TITLE.equals(book.getTitle()) &&
                        Objects.equals(book.getCreatedYear(), CREATED_YEAR) &&
                        isCorrectGenre(book.getGenre()) &&
                        isCorrectAuthor(book.getAuthor())
        ));
    }

    @Test
    @DisplayName("Создание книги с несуществующим автором должно завершаться ошибкой")
    public void addingBookWithNonExistedAuthorIdShouldThrowException() {
        Mockito.when(genreService.getGenre(GENRE_ID)).thenThrow(ObjectNotFoundException.class);
        Mockito.when(authorService.getAuthor(AUTHOR_ID)).thenReturn(new Author(AUTHOR_ID, AUTHOR_NAME, BIRTHDAY, DEATHDAY));

        assertThrows(ObjectNotFoundException.class, () -> bookService.addBook(CORRECT_BOOK_FIELDS));
    }

    @Test
    @DisplayName("Создание книги с несуществующим жанром должно завершаться ошибкой")
    public void addingBookWithNonExistedGenreIdShouldThrowException() {
        Mockito.when(genreService.getGenre(GENRE_ID)).thenReturn(new Genre(GENRE_ID, GENRE_NAME));
        Mockito.when(authorService.getAuthor(AUTHOR_ID)).thenThrow(ObjectNotFoundException.class);

        assertThrows(ObjectNotFoundException.class, () -> bookService.addBook(CORRECT_BOOK_FIELDS));
    }

    @Test
    @DisplayName("Удаление несуществующей книги не должно завершаться ошибкой")
    public void deleteNotExistedBookShouldNotThrowException() {
        Mockito.when(bookRepository.findById(NOT_EXISTED_BOOK_ID)).thenReturn(Optional.empty());

        bookService.removeBook(NOT_EXISTED_BOOK_ID);
    }

    @Test
    @DisplayName("Изменение заголовка несуществующей книги должно завершаться ошибкой")
    public void changeTitleNotExistedBookShouldThrowException() {
        String newTitle = "новое название";

        Mockito.when(bookRepository.findById(NOT_EXISTED_BOOK_ID)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> bookService.updateBookTitle(Pair.of(NOT_EXISTED_BOOK_ID, newTitle)));
    }

    private boolean isCorrectAuthor(Author author) {
        return Objects.equals(author.getId(), AUTHOR_ID) &&
                AUTHOR_NAME.equals(author.getName());
    }

    private boolean isCorrectGenre(Genre genre) {
        return Objects.equals(genre.getId(), GENRE_ID) &&
                GENRE_NAME.equals(genre.getName());
    }
}