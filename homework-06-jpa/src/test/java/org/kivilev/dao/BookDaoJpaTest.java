package org.kivilev.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.BookComment;
import org.kivilev.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class, BookCommentDaoJpa.class})
class BookDaoJpaTest {

    private static final long NOT_EXISTED_BOOK_ID = -100;
    private List<Genre> genres;
    private List<Author> authors;

    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    GenreDao genreDao;

    @Autowired
    BookCommentDao bookCommentDao;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    void getAllDictionaries() {
        genres = genreDao.getAllGenres();
        authors = authorDao.getAllAuthors();
    }

    @Test
    @DisplayName("Получение всех книг должно вернуть все существующие книги")
    public void getAllBooksShouldReturnAllExistedBooks() {
        var expectedBookCount = 3;
        Book book1 = new Book(null, "Book title 1", 1988, getAuthor(0), getGenre(0), Collections.emptyList());
        Book book2 = new Book(null, "Book title 2", 1900, getAuthor(1), getGenre(1), Collections.emptyList());
        Book book3 = new Book(null, "Book title 3", 1922, getAuthor(2), getGenre(2), Collections.emptyList());
        createBook(book1);
        createBook(book2);
        createBook(book3);

        var actualBooks = bookDao.getAllBooks().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .collect(Collectors.toList());

        assertEquals(expectedBookCount, actualBooks.size());
        assertBook(book1, actualBooks.get(0));
        assertBook(book2, actualBooks.get(1));
        assertBook(book3, actualBooks.get(2));
    }

    @Test
    @DisplayName("Получение существующей книги должно вернуть объект книга")
    public void gettingExistedBookShouldReturnBook() {
        Book book = getDefaultBook();
        Long bookId = createBook(book).getId();

        var actualBookOptional = bookDao.getBook(bookId);

        assertTrue(actualBookOptional.isPresent());
        assertBook(book, actualBookOptional.get());
    }

    @Test
    @DisplayName("Получение не существующей книги должно вернуть пустой объект")
    public void gettingNotExistedBookShouldReturnEmptyOptional() {
        var actualBookOptional = bookDao.getBook(NOT_EXISTED_BOOK_ID);

        assertFalse(actualBookOptional.isPresent());
    }

    @Test
    @DisplayName("Получение книги с комментариями возвращает все комментарии к книге")
    public void gettingBookWithCommentsShouldReturnAllExistingComments() {
        var expectedCommentCount = 2;
        Book book = getDefaultBook();
        book = createBook(book);
        Long actualBookId = book.getId();
        BookComment bookComment1 = new BookComment(null, actualBookId, "Text1", LocalDateTime.of(2021, 1, 1, 1, 1, 1));
        BookComment bookComment2 = new BookComment(null, actualBookId, "Text2", LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        bookComment1 = bookCommentDao.save(bookComment1);
        bookComment2 = bookCommentDao.save(bookComment2);
        entityManager.refresh(book);

        var actualBookOptional = bookDao.getBook(actualBookId);

        assertTrue(actualBookOptional.isPresent());
        var actualBook = actualBookOptional.get();
        assertEquals(expectedCommentCount, actualBook.getComments().size());
        var actualComments = actualBook.getComments().stream().sorted(Comparator.comparingLong(BookComment::getId)).collect(Collectors.toList());
        assertComment(bookComment1, actualComments.get(0));
        assertComment(bookComment2, actualComments.get(1));
    }

    @Test
    @DisplayName("Сохранение книги с валидными значениями должно проходить без ошибок")
    public void savingNewBookWithValidValuesShouldBeCorrected() {
        Book newBook = getDefaultBook();

        newBook = bookDao.save(newBook);

        var actualBookOptional = bookDao.getBook(newBook.getId());
        assertTrue(actualBookOptional.isPresent());
        assertBook(newBook, actualBookOptional.get());
    }

    @Test
    @DisplayName("Удаление существующей книги должно проходить успешно")
    public void deletingExistedBookShouldBeSuccessful() {
        Book newBook = getDefaultBook();
        newBook = bookDao.save(newBook);
        Long bookId = newBook.getId();

        bookDao.delete(newBook);

        var bookOptional = bookDao.getBook(bookId);
        assertFalse(bookOptional.isPresent());
    }

    @Test
    @DisplayName("Удаление книги не в контексте должно возбудить ошибку")
    public void deletingNotExistedBookInPersistenceContextShouldThrowException() {
        Book newBook = getDefaultBook();
        newBook.setId(NOT_EXISTED_BOOK_ID);

        assertThrows(IllegalArgumentException.class, () -> bookDao.delete(newBook));
    }

    @Test
    @DisplayName("Обновление наименования существующей книги должно проходить успешно")
    public void updatingTitleExistedBookShouldBeSuccessful() {
        Book newBook = getDefaultBook();
        newBook = bookDao.save(newBook);
        Long bookId = newBook.getId();
        String newTitle = UUID.randomUUID().toString();

        newBook.setTitle(newTitle);
        bookDao.save(newBook);

        var actualBookOptional = bookDao.getBook(bookId);
        assertTrue(actualBookOptional.isPresent());
        var actualBook = actualBookOptional.get();
        assertEquals(newTitle, actualBook.getTitle());
    }

    private Author getAuthor(int index) {
        return authors.get(index);
    }

    private Genre getGenre(int index) {
        return genres.get(index);
    }

    private Book getDefaultBook() {
        return new Book(null, "Default book title", 1988, getAuthor(0), getGenre(0), Collections.emptyList());
    }

    private Book createBook(Book book) {
        return bookDao.save(book);
    }

    private static void assertBook(Book expectedBook, Book actualBook) {
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getCreatedYear(), actualBook.getCreatedYear());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
    }

    private static void assertComment(BookComment expectedComment, BookComment actualComment) {
        assertEquals(expectedComment.getBookId(), actualComment.getBookId());
        assertEquals(expectedComment.getText(), actualComment.getText());
        assertEquals(expectedComment.getCreateDateTime(), actualComment.getCreateDateTime());
    }
}