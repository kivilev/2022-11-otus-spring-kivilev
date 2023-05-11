/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.BookResponseDto;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.Genre;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookRestController.class)
@Import(BookDtoMapper.class)
class BookRestControllerTest {
    @MockBean
    private BookDtoMapper bookDtoMapper;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Получение списка книг должно вернуть книги")
    public void gettingAllBooksShouldReturnBooks() {
        String authorName1 = "Автор 1";
        String authorId1 = "authorId1";
        String authorName2 = "Автор 2";
        String authorId2 = "authorId2";
        String genreName1 = "Жанр 1";
        String genreId1 = "genreId1";
        String genreName2 = "Жанр 2";
        String genreId2 = "genreId2";

        var book1 = new Book(UUID.randomUUID().toString(), "Название1", 1990, authorId1, "genreId1");
        var book2 = new Book(UUID.randomUUID().toString(), "Название2", 1990, authorId2, "genreId2");

        int expectedSize = 2;
        Mockito.when(bookRepository.findAll()).thenReturn(Flux.just(book1, book2));
        Mockito.when(genreRepository.findById(genreId1)).thenReturn(Mono.just(new Genre(UUID.randomUUID().toString(), genreName1)));
        Mockito.when(genreRepository.findById(genreId2)).thenReturn(Mono.just(new Genre(UUID.randomUUID().toString(), genreName2)));
        Mockito.when(authorRepository.findById(authorId1)).thenReturn(Mono.just(new Author(UUID.randomUUID().toString(), authorName1, LocalDate.now(), null)));
        Mockito.when(authorRepository.findById(authorId2)).thenReturn(Mono.just(new Author(UUID.randomUUID().toString(), authorName2, LocalDate.now(), null)));

        var actualBookResponseDtos = webClient
                .get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookResponseDto.class)
                .hasSize(expectedSize)
                .returnResult().getResponseBody();

        assertNotNull(actualBookResponseDtos);
        assertEquals(book1.getId(), actualBookResponseDtos.get(0).getId());
        assertEquals(authorName1, actualBookResponseDtos.get(0).getAuthorName());
        assertEquals(genreName1, actualBookResponseDtos.get(0).getGenreName());
        assertEquals(book2.getId(), actualBookResponseDtos.get(1).getId());
        assertEquals(authorName2, actualBookResponseDtos.get(1).getAuthorName());
        assertEquals(genreName2, actualBookResponseDtos.get(1).getGenreName());
        Mockito.verify(bookRepository, times(1)).findAll();
        Mockito.verify(genreRepository, times(2)).findById(anyString());
        Mockito.verify(authorRepository, times(2)).findById(anyString());
    }

    @Test
    @DisplayName("Изменение существующей книги должно изменять книгу")
    public void changingExistedBookShouldChangeBook() {
        String bookId = "id1";
        String title = "title1";
        Integer createdYear = 1990;
        String authorId = "authorId1";
        String genreId = "genreId1";

        BookChangeRequestDto dto = new BookChangeRequestDto(bookId, title, createdYear, authorId, genreId);
        when(bookRepository.findById(bookId))
                .thenReturn(Mono.just(new Book(bookId + "old", title + "old", createdYear, authorId + "old", genreId + "old")));
        when(bookDtoMapper.toBook(any(BookChangeRequestDto.class))).thenReturn(new Book(bookId, title, createdYear, authorId, genreId));
        when(bookRepository.save(any()))
                .thenReturn(Mono.just(new Book(bookId, title, createdYear, authorId, genreId)));

        webClient.put()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus()
                .isOk();

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(argThat(
                book -> bookId.equals(book.getId()) && title.equals(book.getTitle()) &&
                        genreId.equals(book.getGenreId()) && authorId.equals(book.getAuthorId())
        ));
    }

    @Test
    @DisplayName("Изменение несуществующей книги должно возвращать ошибку")
    public void changingNotExistedBookShouldReturnError() {
        String bookId = "id1";
        BookChangeRequestDto dto = new BookChangeRequestDto(bookId, "title", 1990, "authorId1", "genreId1");
        when(bookRepository.findById(bookId)).thenReturn(Mono.empty());

        webClient.put()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus()
                .is4xxClientError();

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, never()).save(any());
    }
}