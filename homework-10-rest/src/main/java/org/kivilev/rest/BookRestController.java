/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.rest;

import lombok.RequiredArgsConstructor;
import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.model.Book;
import org.kivilev.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;
    private final BookDtoMapper bookDtoMapper;

    @GetMapping("/api/v1/books")
    public List<Book> listBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/api/v1/books")
    public Book saveNewBook(@Valid @RequestBody NewBookRequestDto dto) {
        return bookService.addBook(bookDtoMapper.newBookRequestDtoToBookFields(dto));
    }

    @PutMapping("/api/v1/books")
    public Book saveEditedBook(@Valid @RequestBody BookChangeRequestDto dto) {
        return bookService.changeBook(bookDtoMapper.bookChangeRequestDtoToBookFields(dto));
    }
}
