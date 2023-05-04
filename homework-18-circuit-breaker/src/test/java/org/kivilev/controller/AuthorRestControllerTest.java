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
import org.kivilev.model.Author;
import org.kivilev.rest.AuthorRestController;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(AuthorRestController.class)
class AuthorRestControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение списка авторов должно вернуть авторов")
    @SuppressFBWarnings
    public void gettingAllAuthorsShouldReturnAuthors() throws Exception {
        var authors = List.of(
                new Author(1L, UUID.randomUUID().toString(), LocalDate.now(), null),
                new Author(2L, UUID.randomUUID().toString(), LocalDate.now(), null)
        );
        final int expectedSize = 2;
        Mockito.when(authorService.getAllAuthors()).thenReturn(authors);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/authors"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var actualAuthors = OBJECT_MAPPER.readValue(content, new TypeReference<List<Author>>() {
        });
        assertEquals(expectedSize, actualAuthors.size());
        assertThat(actualAuthors.get(0)).usingRecursiveComparison().isEqualTo(authors.get(0));
        assertThat(actualAuthors.get(1)).usingRecursiveComparison().isEqualTo(authors.get(1));
    }
}