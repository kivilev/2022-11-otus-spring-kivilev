/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.mapper;

import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookDtoMapper {

    public Book toBook(NewBookRequestDto bookDto) {
        return new Book(null,
                bookDto.getTitle(),
                bookDto.getCreatedYear(),
                bookDto.getAuthorId(),
                bookDto.getGenreId()
        );
    }

    public Book toBook(BookChangeRequestDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getCreatedYear(),
                bookDto.getAuthorId(),
                bookDto.getGenreId()
        );
    }
}
