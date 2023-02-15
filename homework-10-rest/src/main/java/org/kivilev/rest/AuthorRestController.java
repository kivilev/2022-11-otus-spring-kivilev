/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.rest;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Author;
import org.kivilev.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorRestController {
    private final AuthorService authorService;

    @GetMapping("/api/v1/authors")
    public List<Author> listAuthors() {
        return authorService.getAllAuthors();
    }
}
