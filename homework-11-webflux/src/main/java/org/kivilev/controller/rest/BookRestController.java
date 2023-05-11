/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import lombok.RequiredArgsConstructor;
import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.BookResponseDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BookRestController {
    private final BookDtoMapper bookDtoMapper;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @GetMapping("/api/v1/books")
    public Flux<BookResponseDto> listBooks() {
        return bookRepository
                .findAll()
                .map(book -> new BookResponseDto(book.getId(), book.getTitle(), book.getCreatedYear(), book.getAuthorId(), book.getGenreId()))
                .flatMap(book -> genreRepository.findById(book.getGenreName())
                        .map(genre -> {
                            book.setGenreName(genre.getName());
                            return book;
                        }))
                .flatMap(book -> authorRepository.findById(book.getAuthorName())
                        .map(author -> {
                            book.setAuthorName(author.getName());
                            return book;
                        }));
    }

    @PostMapping("/api/v1/books")
    public Mono<Book> saveNewBook(@Valid @RequestBody NewBookRequestDto dto) {
        return Mono
                .just(dto)
                .map(bookDtoMapper::toBook)
                .flatMap(bookRepository::save);
    }

    @PutMapping("/api/v1/books")
    public Mono<Book> saveEditedBook(@Valid @RequestBody BookChangeRequestDto dto) {
        return Mono
                .just(dto)
                .flatMap(bookDto -> bookRepository.findById(bookDto.getId()))
                .switchIfEmpty(Mono.error(ObjectNotFoundException::new))
                .map(b -> bookDtoMapper.toBook(dto))
                .flatMap(bookRepository::save);
        //TODO: возможно, можно красивей написать
    }
}
