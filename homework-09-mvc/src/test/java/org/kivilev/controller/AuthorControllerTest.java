/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.Author;
import org.kivilev.service.AuthorService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка авторов должно вернуть станицу со списком авторов")
    public void gettingAllAuthorsShouldReturnPageWithListAuthors() throws Exception {
        var authors = List.of(
                new Author(1L, UUID.randomUUID().toString(), LocalDate.now(), null),
                new Author(2L, UUID.randomUUID().toString(), LocalDate.now(), null)
        );
        Mockito.when(authorService.getAllAuthors()).thenReturn(authors);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("authors"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(authors.get(0).getName()));
        assertTrue(content.contains(authors.get(1).getName()));
    }
}