/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.controller.dto.NewBookDtoRequest;
import org.kivilev.controller.mapper.BookDtoMapper;
import org.kivilev.service.BookService;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kivilev.controller.ControllerTestUtils.createDefaultBook;
import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private BookDtoMapper bookDtoMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка книг должно вернуть станицу со списком книг")
    public void gettingAllBooksShouldReturnPageWithListBooks() throws Exception {
        var books = List.of(createDefaultBook(1L), createDefaultBook(2L));
        Mockito.when(bookService.getAllBooks()).thenReturn(books);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(books.get(0).getTitle()));
        assertTrue(content.contains(books.get(1).getTitle()));
    }

    @Test
    @DisplayName("Отправка корректных данных книги создает книгу")
    public void sendingCorrectBookDataShouldCreateBook() throws Exception {
        NewBookDtoRequest newBookDtoRequest = new NewBookDtoRequest("title", 1111, 1L, 2L);

        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", newBookDtoRequest.getTitle())
                        .param("createdYear", String.valueOf(newBookDtoRequest.getCreatedYear()))
                        .param("authorId", String.valueOf(newBookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(newBookDtoRequest.getGenreId()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));

        Mockito.verify(bookDtoMapper, Mockito.times(1)).toBookFields(argThat(
                        actualBookDtoRequest ->
                                newBookDtoRequest.getTitle().equals(actualBookDtoRequest.getTitle()) &&
                                        newBookDtoRequest.getCreatedYear().equals(actualBookDtoRequest.getCreatedYear()) &&
                                        newBookDtoRequest.getAuthorId().equals(actualBookDtoRequest.getAuthorId()) &&
                                        newBookDtoRequest.getGenreId().equals(actualBookDtoRequest.getGenreId())
                )
        );
        Mockito.verify(bookService, Mockito.times(1)).addBook(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Отправка не корректных данных книги должно вернуть страницу с ошибками")
    public void sendingIncorrectBookDataShouldReturnPageWithErrors() throws Exception {
        NewBookDtoRequest newBookDtoRequest = new NewBookDtoRequest("", 1111, -1L, 2L);

        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", newBookDtoRequest.getTitle())
                        .param("createdYear", String.valueOf(newBookDtoRequest.getCreatedYear()))
                        .param("authorId", String.valueOf(newBookDtoRequest.getAuthorId()))
                        .param("genreId", String.valueOf(newBookDtoRequest.getGenreId()))
                )
                .andExpect(status().isOk())
                .andExpect(view().name("book-add"));

        Mockito.verify(bookService, Mockito.never()).addBook(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("Отправка корректного названия для изменение меняет название книги")
    public void sendingCorrectTitleShouldChangeTitleBook() throws Exception {
        final Long bookId = 1L;
        final String newTitle = UUID.randomUUID().toString();

        mockMvc.perform(post("/books/edit?id=" + bookId)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookId", String.valueOf(bookId))
                        .param("title", newTitle)
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/books"));

        Mockito.verify(bookService, Mockito.times(1)).updateBookTitle(
                Pair.of(bookId, newTitle)
        );
    }
}