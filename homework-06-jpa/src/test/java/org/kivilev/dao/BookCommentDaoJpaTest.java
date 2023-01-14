package org.kivilev.dao;

import org.junit.jupiter.api.Test;
import org.kivilev.model.BookComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@DataJpaTest
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class, BookCommentDaoJpa.class})
class BookCommentDaoJpaTest {
    @Autowired
    BookCommentDao bookCommentDao;
    @Autowired
    BookDao bookDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    GenreDao genreDao;

    @MockBean
    private final Clock CLOCK = Clock.fixed(Instant.parse("2022-01-01T01:00:00Z"), ZoneOffset.UTC);

    @Test
    public void createCommentWithCorrectParamsShouldSaveCorrectly() {
        BookComment bookComment1 = new BookComment(null, 1L, "Текст комментария 1", LocalDateTime.now(CLOCK));
        bookComment1 = bookCommentDao.save(bookComment1);
        bookComment1 = bookCommentDao.save(bookComment1);
    }
}