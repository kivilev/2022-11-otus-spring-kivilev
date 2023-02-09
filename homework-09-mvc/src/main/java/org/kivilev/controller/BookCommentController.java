/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("comments")
public class BookCommentController {

    private final BookCommentService bookCommentService;
    private final BookService bookService;

    @GetMapping
    public String listComments(@RequestParam("bookId") Long bookId, Model model) {
        var book = bookService.getBook(bookId);
        var comments = book.getComments();
        model.addAttribute("book_id", book.getId());
        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping
    public String addComment(@RequestParam("bookId") Long bookId,
                              @Valid @ModelAttribute("comment") NewCommentRequestDto dto,
                              Model model) {

        bookCommentService.addComment(Pair.of(dto.getBookId(), dto.getText()));

        var book = bookService.getBook(bookId);
        var comments = book.getComments();
        model.addAttribute("book_id", book.getId());
        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("comments", comments);
        return "comments";
    }

    @PostMapping("/delete")
    public String removeComment(@RequestParam("bookId") Long bookId,
                             Long commentId,
                             Model model) {

        bookCommentService.removeComment(bookId, commentId);

        var book = bookService.getBook(bookId);
        var comments = book.getComments();
        model.addAttribute("book_id", book.getId());
        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("comments", comments);
        return "comments";
    }
}

