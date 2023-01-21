package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJpa implements AuthorDao {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Author> getAllAuthors() {
        return entityManager.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> getAuthor(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }
}
