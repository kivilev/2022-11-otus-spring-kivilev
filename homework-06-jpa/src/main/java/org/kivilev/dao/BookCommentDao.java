package org.kivilev.dao;

import org.kivilev.model.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentDao {
    BookComment save(BookComment bookComment);

    void delete(BookComment bookComment);

    List<BookComment> getComments(Long bookId);

    Optional<BookComment> getComment(long commentId);
}
