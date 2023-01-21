package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.BookComment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<BookComment> getComment(long commentId) {
        return Optional.ofNullable(entityManager.find(BookComment.class, commentId));
    }
}
