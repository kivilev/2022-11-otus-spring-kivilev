package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Genre;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Genre> getAllGenres() {
        return entityManager.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> getGenre(Long id) {
        return Optional.ofNullable(entityManager.find(Genre.class, id));
    }
}
