/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.AuthorService;
import org.kivilev.service.BookService;
import org.kivilev.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    @GetMapping("books")
    public String listBooks(Model model) {
        return "books";
    }

    @GetMapping("books/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        var book = bookService.getBook(id);
        model.addAttribute("book", book);
        fillModelWithDictionaries(model);
        return "book-edit";
    }

    @GetMapping("books/add")
    public String newPage(Model model) {
        fillModelWithDictionaries(model);
        return "book-add";
    }

    private void fillModelWithDictionaries(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
    }
}
