package org.kivilev.integration;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.config.ApplicationConfig;
import org.kivilev.config.LibraryProperties;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.service.AuthorServiceImpl;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.BookCommentServiceImpl;
import org.kivilev.service.BookService;
import org.kivilev.service.BookServiceImpl;
import org.kivilev.service.GenreServiceImpl;
import org.kivilev.ui.model.BookFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@Import(value = {BookServiceImpl.class, GenreServiceImpl.class, AuthorServiceImpl.class, BookCommentServiceImpl.class, ApplicationConfig.class})
//@ImportAutoConfiguration(TransactionAutoConfiguration.class) // TODO: по идеи вкл транзакционность, но так и не завелось
class BookServiceIntegrationTest {

    private static final Integer CREATED_YEAR = 2020;
    private static final String TITLE = "Название";
    private static final String AUTHOR_ID = "1";
    private static final String GENRE_ID = "2";
    private static final Map<BookFields, Object> CORRECT_BOOK_FIELDS = Map.of(
            BookFields.TITLE, TITLE,
            BookFields.CREATED_YEAR, CREATED_YEAR,
            BookFields.AUTHOR_ID, AUTHOR_ID,
            BookFields.GENRE_ID, GENRE_ID
    );

    @Autowired
    private BookService bookService;
    @Autowired
    private BookCommentService bookCommentService;
    @Autowired
    private BookCommentRepository bookCommentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryProperties libraryProperties;

    @AfterEach
    void cleanUp() {
        // WORKAROUND: чистим после каждого теста. Не смог заставить транзакционно работать каждый тест
        bookCommentRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("Топ N комментариев должно правильно записываться в книге при добавлении нового комментария")
    public void topCommentsShouldBeCorrectAfterAddingNewComment() {
        final int expectedCommentsCount = 4;
        final int expectedTopCommentCount = libraryProperties.getCountTopComments();
        final String comment1Text = "Comment1";
        final String comment2Text = "Comment2";
        final String comment3Text = "Comment3";
        final String comment4Text = "Comment4";

        var book = bookService.addBook(CORRECT_BOOK_FIELDS);
        bookCommentService.addComment(Pair.of(book.getId(), comment1Text));
        bookCommentService.addComment(Pair.of(book.getId(), comment2Text));
        bookCommentService.addComment(Pair.of(book.getId(), comment3Text));
        bookCommentService.addComment(Pair.of(book.getId(), comment4Text));

        var actualComments = bookCommentRepository.findAll();
        var actualBook = bookService.getBook(book.getId());
        var comments = actualBook.getLastTopComments();

        assertEquals(expectedCommentsCount, actualComments.size());
        assertEquals(expectedTopCommentCount, comments.size());
        var actualFirstComment = comments.get(0);
        var actualSecondComment = comments.get(1);
        assertEquals(comment4Text, actualFirstComment.getText());
        assertEquals(comment3Text, actualSecondComment.getText());
    }
}