/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.model.BookComment;
import org.kivilev.service.BookCommentService;
import org.kivilev.service.BookService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kivilev.controller.ControllerTestUtils.createDefaultBook;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookCommentController.class)
class BookCommentControllerTest {

    @MockBean
    private BookCommentService bookCommentService;
    @MockBean
    private BookService bookService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Получение всех комментариев для книги возвращает страницу с комментариями")
    public void gettingAllCommentsShouldReturnPageWithComments() throws Exception {
        long bookId = 1L;
        var book = createDefaultBook(bookId);
        Mockito.when(bookService.getBook(bookId)).thenReturn(book);

        var content = mockMvc.perform(MockMvcRequestBuilders.get("/comments?bookId=" + bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("comments"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(book.getComments().get(0).getText()));
        assertTrue(content.contains(book.getComments().get(1).getText()));
    }

    @PostMapping
    public String addComment(@RequestParam("bookId") Long bookId,
                             @Valid @ModelAttribute("new_comment") NewCommentRequestDto dto,
                             Model model) {

        bookCommentService.addComment(Pair.of(dto.getBookId(), dto.getText()));

        var book = bookService.getBook(bookId);
        var comments = book.getComments();
        model.addAttribute("book_id", book.getId());
        model.addAttribute("book_title", book.getTitle());
        model.addAttribute("comments", comments);
        return "comments";
    }

    @Test
    @DisplayName("Отправка нового комментария создает новый комментарий и добавляет к книге")
    public void sendingCorrectCommentShouldCreateComment() throws Exception {
        long bookId = 1L;
        final String commentText = UUID.randomUUID().toString();

        var book = createDefaultBook(bookId);
        var newComments = new ArrayList<>(book.getComments());
        newComments.add(new BookComment(3L, commentText, LocalDateTime.now()));
        book.setComments(newComments);
        Mockito.when(bookService.getBook(bookId)).thenReturn(book);

        var content = mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("bookId", String.valueOf(bookId))
                        .param("text", commentText)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("comments"))
                .andReturn().getResponse().getContentAsString();

        assertTrue(content.contains(commentText));
        Mockito.verify(bookCommentService, times(1)).addComment(Pair.of(bookId, commentText));
        Mockito.verify(bookService, times(1)).getBook(bookId);
    }
}