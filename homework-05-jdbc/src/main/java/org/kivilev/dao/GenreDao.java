package org.kivilev.dao;

import org.kivilev.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> getAllGenres();

    Optional<Genre> getGenre(Long id);
}
