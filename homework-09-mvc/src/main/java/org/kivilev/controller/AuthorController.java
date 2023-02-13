/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("authors")
    public String listAuthors(Model model) {
        var authors = authorService.getAllAuthors();
        model.addAttribute("authors", authors);
        return "authors";
    }
}
