package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.BookComment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCommentDaoJpa implements BookCommentDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public BookComment save(BookComment bookComment) {
        if (bookComment.getId() == null) {
            entityManager.persist(bookComment);
            return bookComment;
        } else {
            return entityManager.merge(bookComment);
        }
    }

    @Override
    public void delete(BookComment bookComment) {
        entityManager.remove(bookComment);
    }

    @Override
    public List<BookComment> getComments(Long bookId) {
        var query = entityManager.createQuery("select bc from BookComment bc where bc.bookId = :bookId", BookComment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public Optional<BookComment> getComment(long commentId) {
        return Optional.ofNullable(entityManager.find(BookComment.class, commentId));
    }
}
