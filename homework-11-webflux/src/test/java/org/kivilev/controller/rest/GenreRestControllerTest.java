/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.model.Genre;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@WebFluxTest(controllers = GenreRestController.class)
class GenreRestControllerTest {
    @MockBean
    GenreRepository repository;
    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Получение списка жанров должно вернуть жанры")
    public void gettingAllGenresShouldReturnGenres() throws Exception {
        var genre1 = new Genre(UUID.randomUUID().toString(), "Жанр1");
        var genre2 = new Genre(UUID.randomUUID().toString(), "Жанр2");
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Flux.just(genre1, genre2));

        var actualGenres = webClient
                .get()
                .uri("/api/v1/genres")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Genre.class)
                .hasSize(expectedSize)
                .returnResult().getResponseBody();

        assertNotNull(actualGenres);
        assertThat(actualGenres.get(0)).usingRecursiveComparison().isEqualTo(genre1);
        assertThat(actualGenres.get(1)).usingRecursiveComparison().isEqualTo(genre2);
        Mockito.verify(repository, times(1)).findAll();
    }
}