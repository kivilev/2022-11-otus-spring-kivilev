/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.controller.dto.BookChangeTitleDtoRequest;
import org.kivilev.controller.dto.NewBookDtoRequest;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.model.Book;
import org.kivilev.service.BookService;
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
        return "book-title-edit";
    }

    @PostMapping("books/edit")
    public String saveTitle(@Valid @ModelAttribute("book") BookChangeTitleDtoRequest dto,
                            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "book-title-edit";
        }
        bookService.updateBookTitle(Pair.of(dto.getId(), dto.getTitle()));
        return "redirect:/books";
    }

    @GetMapping("books/add")
    public String newPage(NewBookDtoRequest dto, Model model) {
        model.addAttribute("book", new Book());
        return "book-add";
    }

    @PostMapping("books/add")
    public String saveNewBook(@Valid @ModelAttribute("book") NewBookDtoRequest dto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "book-add";
        }
        bookService.addBook(bookDtoMapper.toBookFields(dto));
        return "redirect:/books";
    }
}
