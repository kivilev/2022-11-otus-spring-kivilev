package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.dao.GenreDao;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao genreDao;

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    @Override
    public Genre getGenre(Long id) {
        var genreOptional = genreDao.getGenre(id);
        return genreOptional.orElseThrow(ObjectNotFoundException::new);
    }
}
