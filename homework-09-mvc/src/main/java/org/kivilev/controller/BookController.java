/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.model.Book;
import org.kivilev.service.AuthorService;
import org.kivilev.service.BookService;
import org.kivilev.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final BookDtoMapper bookDtoMapper;

    @GetMapping("books")
    public String listBooks(Model model) {
        var books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("books/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        var book = bookService.getBook(id);
        model.addAttribute("book", book);
        fillModelWithDictionaries(model);
        return "book-edit";
    }

    @PostMapping("books/edit")
    public String saveEditedBook(@Valid @ModelAttribute("book") BookChangeRequestDto dto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            fillModelWithDictionaries(model);
            return "book-edit";
        }
        bookService.changeBook(bookDtoMapper.bookChangeRequestDtoToBookFields(dto));
        return "redirect:/books";
    }

    @GetMapping("books/add")
    public String newPage(NewBookRequestDto dto, Model model) {
        model.addAttribute("book", new Book());
        fillModelWithDictionaries(model);
        return "book-add";
    }

    @PostMapping("books/add")
    public String saveNewBook(@Valid @ModelAttribute("book") NewBookRequestDto dto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            fillModelWithDictionaries(model);
            return "book-add";
        }
        bookService.addBook(bookDtoMapper.newBookRequestDtoToBookFields(dto));
        return "redirect:/books";
    }

    private void fillModelWithDictionaries(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("genres", genreService.getAllGenres());
    }
}
