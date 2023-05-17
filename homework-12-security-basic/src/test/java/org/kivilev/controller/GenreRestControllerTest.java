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
import org.kivilev.model.Genre;
import org.kivilev.rest.GenreRestController;
import org.kivilev.service.GenreService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(GenreRestController.class)
class GenreRestControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка жанров должно вернуть жанры")
    @SuppressFBWarnings
    @WithMockUser
    public void gettingAllGenresShouldReturnGenres() throws Exception {
        final int expectedSize = 2;
        var genres = List.of(
                new Genre(1L, UUID.randomUUID().toString()),
                new Genre(2L, UUID.randomUUID().toString())
        );
        Mockito.when(genreService.getAllGenres()).thenReturn(genres);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/genres"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var actualGenres = OBJECT_MAPPER.readValue(content, new TypeReference<List<Genre>>() {
        });
        assertEquals(expectedSize, actualGenres.size());
        assertThat(actualGenres.get(0)).usingRecursiveComparison().isEqualTo(genres.get(0));
        assertThat(actualGenres.get(1)).usingRecursiveComparison().isEqualTo(genres.get(1));
    }
}