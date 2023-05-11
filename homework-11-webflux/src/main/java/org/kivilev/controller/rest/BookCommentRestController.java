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
import org.kivilev.controller.dto.NewCommentRequestDto;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.BookComment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequiredArgsConstructor
public class BookCommentRestController {
    private final BookCommentRepository bookCommentRepository;
    private final Clock clock;

    @GetMapping("/api/v1/comments")
    public Flux<BookComment> listComments(@RequestParam("bookId") String bookId) {
        return bookCommentRepository.findAllByBookId(bookId);
    }

    @PostMapping("/api/v1/comments")
    public void addComment(@Valid @RequestBody NewCommentRequestDto dto) {
        Mono.just(dto)
                .map(d -> new BookComment(d.getText(), LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS), d.getBookId()))
                .flatMap(bookCommentRepository::save)
                .subscribe();
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public Mono<Void> removeComment(@PathVariable("id") String commentId) {
        return bookCommentRepository.findById(commentId)
                .switchIfEmpty(Mono.error(ObjectNotFoundException::new))
                .flatMap(bookCommentRepository::delete);
    }
}
