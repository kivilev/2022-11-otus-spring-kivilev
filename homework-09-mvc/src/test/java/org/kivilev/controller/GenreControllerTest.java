/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.Genre;
import org.kivilev.service.GenreService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка жанров должно вернуть станицу со списком жанров")
    public void gettingAllGenresShouldReturnPageWithListGenres() throws Exception {
        var genres = List.of(
                new Genre(1L, UUID.randomUUID().toString()),
                new Genre(2L, UUID.randomUUID().toString())
        );
        Mockito.when(genreService.getAllGenres()).thenReturn(genres);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("genres"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(genres.get(0).getName()));
        assertTrue(content.contains(genres.get(1).getName()));
    }
}