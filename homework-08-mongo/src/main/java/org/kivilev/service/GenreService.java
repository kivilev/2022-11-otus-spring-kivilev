package org.kivilev.service;

import org.kivilev.model.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre getGenre(String id);
}
