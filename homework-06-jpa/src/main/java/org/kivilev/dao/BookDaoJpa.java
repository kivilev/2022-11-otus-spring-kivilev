package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Book;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Book> getAllBooks() {
        return entityManager.createQuery(
                "select b from Book b " +
                        " join fetch b.author " +
                        " join fetch b.genre",
                Book.class).getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            entityManager.persist(book);
            return book;
        } else {
            return entityManager.merge(book);
        }
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }

    @Override
    public Optional<Book> getBook(long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }
}
