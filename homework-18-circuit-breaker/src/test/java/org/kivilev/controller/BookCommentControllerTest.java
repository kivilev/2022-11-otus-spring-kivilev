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
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.model.BookComment;
import org.kivilev.rest.BookCommentRestController;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.BookService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.kivilev.controller.ControllerTestUtils.createDefaultBook;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookCommentRestController.class)
class BookCommentControllerTest {
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
    @MockBean
    private BookCommentService bookCommentService;
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех комментариев для книги возвращает комментарии")
    @SuppressFBWarnings
    public void gettingAllBooksShouldReturnBooks() throws Exception {
        final int expectedSize = 2;
        long bookId = 1L;
        var book = createDefaultBook(bookId);
        Mockito.when(bookService.getBook(bookId)).thenReturn(book);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/comments?bookId=" + bookId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();

        var actualBookComments = OBJECT_MAPPER.readValue(content, new TypeReference<List<BookComment>>() {
        });
        assertEquals(expectedSize, actualBookComments.size());
        assertThat(actualBookComments.get(0)).usingRecursiveComparison().isEqualTo(book.getComments().get(0));
        assertThat(actualBookComments.get(1)).usingRecursiveComparison().isEqualTo(book.getComments().get(1));
    }

    @Test
    @DisplayName("Отправка нового комментария создает новый комментарий и добавляет к книге")
    public void sendingCorrectCommentShouldCreateComment() throws Exception {
        long bookId = 1L;
        final String commentText = UUID.randomUUID().toString();
        NewCommentRequestDto dto = new NewCommentRequestDto(bookId, commentText);

        mockMvc.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(dto))
                )
                .andExpect(status().isOk());

        Mockito.verify(bookCommentService, times(1)).addComment(Pair.of(bookId, commentText));
    }

    @Test
    @DisplayName("Удаление существующего комментария удаляет комментарий")
    public void deletingRequestShouldDeleteComment() throws Exception {
        long bookId = 1L;
        long commentId = 2L;

        mockMvc.perform(delete(String.format("/api/v1/comments?bookId=%s&commentId=%s", bookId, commentId))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Mockito.verify(bookCommentService, times(1)).removeComment(bookId, commentId);
    }
}