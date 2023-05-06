/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.rest;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.model.BookComment;
import org.kivilev.service.BookCommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookCommentRestController {

    private final BookCommentService bookCommentService;

    @GetMapping("/api/v1/comments")
    public List<BookComment> listComments(@RequestParam("bookId") String bookId) {
        return bookCommentService.getAllComments(bookId);
    }

    @PostMapping("/api/v1/comments")
    public void addComment(@Valid @RequestBody NewCommentRequestDto dto) {
        bookCommentService.addComment(Pair.of(dto.getBookId(), dto.getText()));
    }

    @DeleteMapping("/api/v1/comments")
    public void removeComment(@Valid @RequestParam("bookId") String bookId,
                              @RequestParam("commentId") String commentId) {
        bookCommentService.removeComment(commentId);
    }
}

