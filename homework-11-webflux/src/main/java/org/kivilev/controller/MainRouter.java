/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
@RequiredArgsConstructor
public class MainRouter {
    @Value("classpath:/static/index.html")
    private Resource indexHtml;
    @Value("classpath:/templates/authors.html")
    private Resource authorsHtml;
    @Value("classpath:/templates/genres.html")
    private Resource genresHtml;
    @Value("classpath:/templates/books.html")
    private Resource booksHtml;
    @Value("classpath:/templates/book-add.html")
    private Resource bookAddHtml;
    @Value("classpath:/templates/book-edit.html")
    private Resource bookEditHtml;
    @Value("classpath:/templates/comments.html")
    private Resource commentsHtml;

    @Bean
    public RouterFunction<ServerResponse> staticPages() {
        return route()
                .GET("/",
                        request -> ok()
                                .contentType(MediaType.TEXT_HTML)
                                .bodyValue(indexHtml))
                .GET("/books",
                        request -> ok()
                                .contentType(MediaType.TEXT_HTML)
                                .bodyValue(booksHtml))
                .GET("/books/add", request -> ok()
                        .contentType(MediaType.TEXT_HTML)
                        .bodyValue(bookAddHtml))
                .GET("/books/edit",
                        request -> request.queryParam("id")
                                .map(p -> ok()
                                        .contentType(MediaType.TEXT_HTML)
                                        .bodyValue(bookEditHtml))
                                .orElse(badRequest()
                                        .build()))
                .GET("/comments",
                        request -> request.queryParam("bookId")
                                .map(p -> ok()
                                        .contentType(MediaType.TEXT_HTML)
                                        .bodyValue(commentsHtml))
                                .orElse(badRequest()
                                        .build()))
                .GET("/authors",
                        request -> ok()
                                .contentType(MediaType.TEXT_HTML)
                                .bodyValue(authorsHtml))
                .GET("/genres",
                        request -> ok()
                                .contentType(MediaType.TEXT_HTML)
                                .bodyValue(genresHtml))
                .build();
    }
}
