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
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
//@Import({BookDaoJpa.class, AuthorDaoRepo.class, GenreDaoJpa.class})
class BookDaoJpaTest {
/*
    private static final long NOT_EXISTED_BOOK_ID = -100;
    private List<Genre> genres = Collections.emptyList();
    private List<Author> authors = Collections.emptyList();
    @Autowired
    private BookDao bookDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void getAllDictionaries() {
        genres = genreDao.getAllGenres();
        authors = authorDao.getAllAuthors();
    }

    @Test
    @DisplayName("Получение всех книг должно вернуть все существующие книги")
    public void getAllBooksShouldReturnAllExistedBooks() {
        final var expectedBookCount = 3;
        Book book1 = new Book(null, "Book title 1", 1988, getAuthor(0), getGenre(0), Collections.singletonList(getDefaultBookComment()));
        Book book2 = getDefaultBook();
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
    @DisplayName("Получение всех книг когда нет данных в БД должно вернуть пустой список")
    public void getAllBooksWithoutDataInDatabaseShouldReturnEmptyList() {
        var expectedBookCount = 0;
        var actualBooks = bookDao.getAllBooks();

        assertEquals(expectedBookCount, actualBooks.size());
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
    @DisplayName("Сохранение книги с валидными значениями должно проходить без ошибок")
    public void savingNewBookWithvaridvaruesShouldBeCorrected() {
        Book newBook = getDefaultBook();

        newBook = bookDao.save(newBook);

        var actualBookOptional = getBook(newBook.getId());
        assertTrue(actualBookOptional.isPresent());
        assertBook(newBook, actualBookOptional.get());
    }

    @Test
    @DisplayName("Удаление существующей книги должно проходить успешно")
    public void deletingExistedBookShouldBeSuccessful() {
        Book newBook = getDefaultBook();
        newBook = createBook(newBook);
        var bookId = newBook.getId();

        bookDao.delete(newBook);

        var bookOptional = getBook(bookId);
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
        var book = createBook(newBook);
        Long bookId = book.getId();

        String newTitle = UUID.randomUUID().toString();
        book.setTitle(newTitle);
        bookDao.save(book);

        var actualBookOptional = getBook(bookId);
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
        List<BookComment> comments = new ArrayList<>();
        comments.add(getDefaultBookComment());
        comments.add(getDefaultBookComment());
        return new Book(null, UUID.randomUUID().toString(), 1988, getAuthor(0), getGenre(0), comments);
    }

    private BookComment getDefaultBookComment() {
        return new BookComment(null, UUID.randomUUID().toString(), LocalDateTime.now());
    }

    private Book createBook(Book book) {
        testEntityManager.persist(book);
        return book;
    }

    private Optional<Book> getBook(Long id) {
        return Optional.ofNullable(testEntityManager.find(Book.class, id));
    }

    private static void assertBook(Book expectedBook, Book actualBook) {
        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getCreatedYear(), actualBook.getCreatedYear());
        assertEquals(expectedBook.getAuthor(), actualBook.getAuthor());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getComments(), actualBook.getComments());

    }*/
}