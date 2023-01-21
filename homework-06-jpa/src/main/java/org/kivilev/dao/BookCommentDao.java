package org.kivilev.dao;

import org.kivilev.model.BookComment;

import java.util.Optional;

public interface BookCommentDao {
    Optional<BookComment> getComment(long commentId);
}
