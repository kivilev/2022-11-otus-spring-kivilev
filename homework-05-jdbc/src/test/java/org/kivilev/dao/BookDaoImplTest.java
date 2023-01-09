package org.kivilev.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@Import(BookDaoImpl.class)
class BookDaoImplTest {

    private static final int EXPECTED_BOOK_COUNT = 4;
    private static final long EXISTED_BOOK_ID_1 = 1L;
    private static final String BOOK_TITLE_1 = "Killing in castle";
    private static final Integer BOOK_CREATED_DATE_1 = 1920;
    private static final Long GENRE_ID_1 = 1L;
    private static final String GENRE_NAME_1 = "детектив";
    private static final long NOT_EXISTED_BOOK_ID = -1L;
    private static final Long AUTHOR_ID_4 = 4L;
    private static final String AUTHOR_NAME_4 = "Агата Кристи";
    private static final LocalDate AUTHOR_BIRTHDAY_4 = LocalDate.of(1899, 2, 1);
    private static final LocalDate AUTHOR_DEATHDAY_4 = LocalDate.of(1968, 1, 1);

    @Autowired
    BookDao bookDao;

    @Test
    @DisplayName("Получение всех книг должно вернуть ожидаемое количество")
    public void getAllBooks() {
        var books = bookDao.getAllBooks();

        assertEquals(EXPECTED_BOOK_COUNT, books.size());
    }

    @Test
    @DisplayName("Получение существующей книги должно вернуть объект книга")
    public void gettingExistedBookShouldReturnBook() {
        var bookOptional = bookDao.getBook(EXISTED_BOOK_ID_1);

        assertBook(bookOptional, EXISTED_BOOK_ID_1);
    }

    @Test
    @DisplayName("Получение не существующей книги должно вернуть пустой объект")
    public void gettingNotExistedBookShouldReturnEmptyOptional() {
        var bookOptional = bookDao.getBook(NOT_EXISTED_BOOK_ID);

        assertFalse(bookOptional.isPresent());
    }

    @Test
    @DisplayName("Сохранение книги с валидными значениями должно проходить без ошибок")
    public void savingNewBookWithValidValuesShouldBeCorrected() {
        Book newBook = new Book(null, BOOK_TITLE_1, BOOK_CREATED_DATE_1,
                new Author(AUTHOR_ID_4, AUTHOR_NAME_4, AUTHOR_BIRTHDAY_4, AUTHOR_DEATHDAY_4),
                new Genre(GENRE_ID_1, GENRE_NAME_1));

        bookDao.save(newBook);

        var bookId = newBook.getId();
        var bookOptional = bookDao.getBook(bookId);
        assertBook(bookOptional, newBook.getId());
    }

    @Test
    @DisplayName("Удаление существующей книги должно проходить успешно")
    public void deletingExistedBookShouldBeSuccessful() {
        bookDao.delete(EXISTED_BOOK_ID_1);

        var bookOptional = bookDao.getBook(EXISTED_BOOK_ID_1);
        assertFalse(bookOptional.isPresent());
    }

    @Test
    @DisplayName("Удаление несуществующей книги не должно возбуждать ошибку")
    public void deletingNotExistedBookShouldNotThrowException() {
        bookDao.delete(NOT_EXISTED_BOOK_ID);
    }

    @Test
    @DisplayName("Обновление наименования существующей книги должно проходить успешно")
    public void updatingTitleExistedBookShouldBeSuccessful() {
        String newTitle = UUID.randomUUID().toString();
        bookDao.updateTitle(EXISTED_BOOK_ID_1, newTitle);

        var bookOptional = bookDao.getBook(EXISTED_BOOK_ID_1);
        assertTrue(bookOptional.isPresent());
        var book = bookOptional.get();
        assertEquals(newTitle, book.getTitle());
    }

    @Test
    @DisplayName("Обновление наименования несуществующей книги не должно возбуждать ошибку")
    public void deletingNotExistedBookShouldNotThrowException2() {
        String newTitle = UUID.randomUUID().toString();
        bookDao.updateTitle(NOT_EXISTED_BOOK_ID, newTitle);
    }

    private static void assertBook(Optional<Book> bookOptional, Long id) {
        assertTrue(bookOptional.isPresent());
        var book = bookOptional.get();
        assertEquals(id, book.getId());
        assertEquals(BOOK_TITLE_1, book.getTitle());
        assertEquals(BOOK_CREATED_DATE_1, book.getCreatedYear());
        assertEquals(GENRE_ID_1, book.getGenre().getId());
        assertEquals(AUTHOR_ID_4, book.getAuthor().getId());
        assertEquals(AUTHOR_NAME_4, book.getAuthor().getName());
        assertEquals(AUTHOR_BIRTHDAY_4, book.getAuthor().getBirthDay());
        assertEquals(AUTHOR_DEATHDAY_4, book.getAuthor().getDeathDay());
    }
}