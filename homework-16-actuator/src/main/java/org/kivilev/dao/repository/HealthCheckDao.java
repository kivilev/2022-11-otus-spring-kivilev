/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.dao.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HealthCheckDao {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public boolean isDictionariesReady() {
        return !authorRepository.findAll(PageRequest.of(0, 1)).isEmpty() &&
                !genreRepository.findAll(PageRequest.of(0, 1)).isEmpty();
    }
}
