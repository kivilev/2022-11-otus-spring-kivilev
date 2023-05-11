/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.model.Author;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

@WebFluxTest(controllers = AuthorRestController.class)
class AuthorRestControllerTest {
    @MockBean
    AuthorRepository repository;
    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Получение списка авторов должно вернуть авторов")
    public void gettingAllAuthorsShouldReturnAuthors() throws Exception {
        var author1 = new Author(UUID.randomUUID().toString(), "Автор 1", LocalDate.now(), null);
        var author2 = new Author(UUID.randomUUID().toString(), "Автор 2", LocalDate.now(), null);
        int expectedSize = 2;
        Mockito.when(repository.findAll()).thenReturn(Flux.just(author1, author2));

        var actualAuthors = webClient
                .get()
                .uri("/api/v1/authors")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Author.class)
                .hasSize(expectedSize)
                .returnResult().getResponseBody();

        assertNotNull(actualAuthors);
        assertThat(actualAuthors.get(0)).usingRecursiveComparison().isEqualTo(author1);
        assertThat(actualAuthors.get(1)).usingRecursiveComparison().isEqualTo(author2);
        Mockito.verify(repository, times(1)).findAll();
    }
}