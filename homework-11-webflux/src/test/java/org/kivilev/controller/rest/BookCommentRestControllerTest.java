/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.config.ApplicationConfig;
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.model.BookComment;
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

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebFluxTest(controllers = BookCommentRestController.class)
@Import(ApplicationConfig.class)
class BookCommentRestControllerTest {
    @MockBean
    BookCommentRepository repository;
    @Autowired
    Clock clock;
    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("Получение всех комментариев для книги возвращает комментарии")
    public void gettingAllCommentsForOneBookShouldReturnAllComments() {
        String bookId = UUID.randomUUID().toString();
        var comment1 = new BookComment("text1", LocalDateTime.now(), bookId);
        var comment2 = new BookComment("text2", LocalDateTime.now(), bookId);
        int expectedSize = 2;
        Mockito.when(repository.findAllByBookId(bookId)).thenReturn(Flux.just(comment1, comment2));

        var actualBookComments = webClient
                .get()
                .uri("/api/v1/comments?bookId=" + bookId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookComment.class)
                .hasSize(expectedSize)
                .returnResult().getResponseBody();

        assertNotNull(actualBookComments);
        assertThat(actualBookComments.get(0)).usingRecursiveComparison().isEqualTo(comment1);
        assertThat(actualBookComments.get(1)).usingRecursiveComparison().isEqualTo(comment2);
        Mockito.verify(repository, times(1)).findAllByBookId(bookId);
    }

    @Test
    @DisplayName("Отправка нового комментария создает новый комментарий и добавляет к книге")
    public void sendingCorrectCommentShouldCreateComment() {
        String bookId = UUID.randomUUID().toString();
        String commentText = UUID.randomUUID().toString();
        NewCommentRequestDto dto = new NewCommentRequestDto(bookId, commentText);

        webClient.post()
                .uri("/api/v1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).save(argThat(
                comment -> bookId.equals(comment.getBookId()) &&
                        commentText.equals(comment.getText())
        ));
    }

    @Test
    @DisplayName("Удаление существующего комментария удаляет комментарий")
    public void deletingRequestShouldDeleteComment() throws Exception {
        String commentId = "11870d19a361414aa32d29cc37158d94";
        String text = "text";
        BookComment comment = new BookComment(text, null, "bookId");
        Mockito.when(repository.findById(commentId)).thenReturn(Mono.just(comment));
        Mockito.when(repository.delete(comment)).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/api/v1/comments/" + commentId)
                .exchange()
                .expectStatus()
                .isOk();

        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Удаление не существующего комментария должен возвращать ошибку")
    public void deletingRequestForNotExistedCommentShouldThrowException() {
        String commentId = "not existed comment";
        Mockito.when(repository.findById(commentId)).thenReturn(Mono.empty());

        webClient.delete()
                .uri("/api/v1/comments/" + commentId)
                .exchange()
                .expectStatus()
                .is4xxClientError();

        verify(repository, never()).delete(any());
        verify(repository, times(1)).findById(commentId);
    }
}
