/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookCommentController {

    private final BookService bookService;

    @GetMapping("comments")
    public String listComments(@RequestParam("bookId") Long bookId, Model model) {
        var book = bookService.getBook(bookId);
        model.addAttribute("book_id", book.getId());
        model.addAttribute("book_title", book.getTitle());
        return "comments";
    }
}

