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
import org.kivilev.model.Genre;
import org.kivilev.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GenreRestController {
    private final GenreService genreService;

    @GetMapping("/api/v1/genres")
    public List<Genre> listGenres() {
        return genreService.getAllGenres();
    }
}
