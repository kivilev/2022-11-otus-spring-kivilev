package org.kivilev.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

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
    private static final Long BOOK_AUTHOR_ID_1 = 4L;
    private static final Long BOOK_GENRE_ID_1 = 1L;
    private static final long NOT_EXISTED_BOOK_ID = -1L;

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
        Book newBook = new Book(null, BOOK_TITLE_1, BOOK_CREATED_DATE_1, new Author(BOOK_AUTHOR_ID_1), new Genre(BOOK_GENRE_ID_1));

        bookDao.save(newBook);
        var bookOptional = bookDao.getBook(newBook.getId());

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
        assertEquals(BOOK_GENRE_ID_1, book.getGenre().getId());
        assertEquals(BOOK_AUTHOR_ID_1, book.getAuthor().getId());
    }
}