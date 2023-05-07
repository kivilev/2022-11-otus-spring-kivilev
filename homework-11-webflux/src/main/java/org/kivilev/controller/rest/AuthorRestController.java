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
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.model.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorRepository authorRepository;

    @GetMapping("/api/v1/authors")
    public Flux<Author> listAuthors() {
        return authorRepository.findAll();
    }
}
