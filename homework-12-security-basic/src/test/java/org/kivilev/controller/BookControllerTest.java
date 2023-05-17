/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.model.Book;
import org.kivilev.rest.BookRestController;
import org.kivilev.service.AuthorService;
import org.kivilev.service.BookService;
import org.kivilev.service.GenreService;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.kivilev.controller.ControllerTestUtils.createDefaultBook;
import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
class BookControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @MockBean
    private BookService bookService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private BookDtoMapper bookDtoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка книг должно вернуть книги")
    @SuppressFBWarnings
    @WithMockUser
    public void gettingAllBooksShouldReturnBooks() throws Exception {
        final int expectedSize = 2;
        var books = List.of(createDefaultBook(1L), createDefaultBook(2L));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var actualBooks = OBJECT_MAPPER.readValue(content, new TypeReference<List<Book>>() {
        });
        assertEquals(expectedSize, actualBooks.size());
        assertThat(actualBooks.get(0)).usingRecursiveComparison().isEqualTo(books.get(0));
        assertThat(actualBooks.get(1)).usingRecursiveComparison().isEqualTo(books.get(1));
    }

    @Test
    @DisplayName("Отправка корректных данных книги создает книгу")
    @WithMockUser
    public void sendingCorrectBookDataShouldCreateBook() throws Exception {
        final String title = UUID.randomUUID().toString();
        final Integer createYear = 1111;
        final Long authorId = 1L;
        final Long genreId = 2L;
        NewBookRequestDto dto = new NewBookRequestDto(
                title,
                createYear,
                authorId,
                genreId
        );

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dto))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Mockito.verify(bookDtoMapper, Mockito.times(1)).newBookRequestDtoToBookFields(argThat(
                        actualBookDtoRequest ->
                                title.equals(actualBookDtoRequest.getTitle()) &&
                                        createYear.equals(actualBookDtoRequest.getCreatedYear()) &&
                                        authorId.equals(actualBookDtoRequest.getAuthorId()) &&
                                        genreId.equals(actualBookDtoRequest.getGenreId())
                )
        );
        Mockito.verify(bookService, Mockito.times(1)).addBook(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Отправка корректных данных книги без авторизации завершается ошибкой")
    public void sendingCorrectBookDataWithoutAuthorizationShouldCreateBook() throws Exception {
        final String title = UUID.randomUUID().toString();
        final Integer createYear = 1111;
        final Long authorId = 1L;
        final Long genreId = 2L;
        NewBookRequestDto dto = new NewBookRequestDto(
                title,
                createYear,
                authorId,
                genreId
        );

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dto))
                        .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Отправка не корректных данных книги должно вернуть страницу с ошибками")
    @WithMockUser
    public void sendingIncorrectBookDataShouldReturnPageWithErrors() throws Exception {
        final String title = "";
        final Integer createYear = 1111;
        final Long authorId = 1L;
        final Long genreId = 2L;
        NewBookRequestDto dto = new NewBookRequestDto(
                title,
                createYear,
                authorId,
                genreId
        );

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dto))
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Отправка корректных данных книги для изменения должен изменить книгу")
    @WithMockUser
    public void sendingCorrectBookDataToEditBookShouldChangeBook() throws Exception {
        final Long bookId = 1L;
        final String title = UUID.randomUUID().toString();
        final Integer createYear = 1111;
        final Long authorId = 2L;
        final Long genreId = 3L;

        BookChangeRequestDto dto = new BookChangeRequestDto(bookId, title, createYear, authorId, genreId);

        mockMvc.perform(put("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dto))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Mockito.verify(bookDtoMapper, Mockito.times(1)).bookChangeRequestDtoToBookFields(argThat(
                        actualBookDtoRequest ->
                                title.equals(actualBookDtoRequest.getTitle()) &&
                                        createYear.equals(actualBookDtoRequest.getCreatedYear()) &&
                                        authorId.equals(actualBookDtoRequest.getAuthorId()) &&
                                        genreId.equals(actualBookDtoRequest.getGenreId())
                )
        );
        Mockito.verify(bookService, Mockito.times(1)).changeBook(ArgumentMatchers.any());
    }

}