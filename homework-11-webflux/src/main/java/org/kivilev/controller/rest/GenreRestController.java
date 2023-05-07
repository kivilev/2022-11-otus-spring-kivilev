/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import lombok.RequiredArgsConstructor;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.model.Genre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreRepository genreRepository;

    @GetMapping("/api/v1/genres")
    public Flux<Genre> listGenres() {
        return genreRepository.findAll();
    }
}
