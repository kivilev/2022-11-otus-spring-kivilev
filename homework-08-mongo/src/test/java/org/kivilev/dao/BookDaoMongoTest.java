package org.kivilev.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.BookComment;
import org.kivilev.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
//@ImportAutoConfiguration(TransactionAutoConfiguration.class) // TODO: по идеи вкл транзакционность, но так и не завелось
class BookDaoMongoTest {
    private static final String NOT_EXISTED_BOOK_ID = "-100";
    private List<Genre> genres = Collections.emptyList();
    private List<Author> authors = Collections.emptyList();
    @Autowired
    private BookRepository bookDao;
    @Autowired
    private AuthorRepository authorDao;
    @Autowired
    private GenreRepository genreDao;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void getAllDictionaries() {
        genres = (List<Genre>) genreDao.findAll();
        authors = (List<Author>) authorDao.findAll();
    }

    @AfterEach
    void cleanUp() {
        // WORKAROUND: чистим после каждого теста. Не смог заставить транзакционно работать каждый тест
        bookCommentRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Получение всех книг должно вернуть все существующие книги")
    public void getAllBooksShouldReturnAllExistedBooks() {
        final var expectedBookCount = 3;
        Book book1 = getDefaultBook();
        Book book2 = getDefaultBook();
        Book book3 = getDefaultBook();
        createBook(book1);
        createBook(book2);
        createBook(book3);

        var actualBooks = new ArrayList<>(bookDao.findAll());

        assertEquals(expectedBookCount, actualBooks.size());
    }

    @Test
    @DisplayName("Получение всех книг когда нет данных в БД должно вернуть пустой список")
    public void getAllBooksWithoutDataInDatabaseShouldReturnEmptyList() {
        var expectedBookCount = 0;
        var actualBooks = bookDao.findAll();

        assertEquals(expectedBookCount, actualBooks.size());
    }

    @Test
    @DisplayName("Получение существующей книги должно вернуть объект книга")
    public void gettingExistedBookShouldReturnBook() {
        Book book = getDefaultBook();
        var bookId = createBook(book).getId();

        var actualBookOptional = bookDao.findById(bookId);

        assertTrue(actualBookOptional.isPresent());

        assertThat(actualBookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName("Получение не существующей книги должно вернуть пустой объект")
    public void gettingNotExistedBookShouldReturnEmptyOptional() {
        var actualBookOptional = bookDao.findById(NOT_EXISTED_BOOK_ID);

        assertFalse(actualBookOptional.isPresent());
    }

    @Test
    @DisplayName("Сохранение книги с валидными значениями должно проходить без ошибок")
    public void savingNewBookWithValidValuesShouldBeCorrected() {
        Book newBook = getDefaultBook();

        newBook = bookDao.save(newBook);

        var actualBookOptional = findById(newBook.getId());
        assertThat(actualBookOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(newBook);
    }

    @Test
    @DisplayName("Удаление существующей книги должно проходить успешно")
    public void deletingExistedBookShouldBeSuccessful() {
        Book newBook = getDefaultBook();
        newBook = createBook(newBook);
        var bookId = newBook.getId();

        bookDao.delete(newBook);

        var bookOptional = findById(bookId);
        assertFalse(bookOptional.isPresent());
    }

    @Test
    @DisplayName("Удаление книги не в контексте не должно возбудить ошибку")
    public void deletingNotExistedBookInPersistenceContextShouldNotThrowException() {
        Book newBook = getDefaultBook();
        newBook.setId(NOT_EXISTED_BOOK_ID);

        bookDao.delete(newBook);
    }

    @Test
    @DisplayName("Обновление наименования существующей книги должно проходить успешно")
    public void updatingTitleExistedBookShouldBeSuccessful() {
        Book newBook = getDefaultBook();
        var book = createBook(newBook);
        var bookId = book.getId();

        String newTitle = UUID.randomUUID().toString();
        book.setTitle(newTitle);
        bookDao.save(book);

        var actualBookOptional = findById(bookId);
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
        var book = new Book(null, UUID.randomUUID().toString(), 1988, getAuthor(0), getGenre(0), Collections.emptyList());
        book = mongoTemplate.save(book);

        List<BookComment> comments = new ArrayList<>();
        comments.add(getDefaultBookComment(book));
        comments.add(getDefaultBookComment(book));
        book.setLastTopComments(comments);

        return book;
    }

    private BookComment getDefaultBookComment(Book book) {
        var bookComment = new BookComment(UUID.randomUUID().toString(), LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), book);
        return bookCommentRepository.save(bookComment);
    }

    private Book createBook(Book book) {
        mongoTemplate.save(book);
        return book;
    }

    private Optional<Book> findById(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, Book.class));
    }
}