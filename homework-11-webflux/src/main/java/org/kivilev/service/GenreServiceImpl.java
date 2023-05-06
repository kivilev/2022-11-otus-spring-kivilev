package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }

    @Override
    public Genre getGenre(String id) {
        var genreOptional = genreRepository.findById(id);
        return genreOptional.orElseThrow(ObjectNotFoundException::new);
    }
}
