/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.mapper;

import org.kivilev.controller.dto.BookChangeRequestDto;
import org.kivilev.controller.dto.NewBookRequestDto;
import org.kivilev.service.model.BookFields;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BookDtoMapper {

    public Map<BookFields, Object> newBookRequestDtoToBookFields(NewBookRequestDto bookDto) {
        return Map.of(
                BookFields.TITLE, bookDto.getTitle(),
                BookFields.CREATED_YEAR, bookDto.getCreatedYear(),
                BookFields.AUTHOR_ID, bookDto.getAuthorId(),
                BookFields.GENRE_ID, bookDto.getGenreId()
        );
    }

    public Map<BookFields, Object> bookChangeRequestDtoToBookFields(BookChangeRequestDto bookDto) {
        return Map.of(
                BookFields.ID, bookDto.getId(),
                BookFields.TITLE, bookDto.getTitle(),
                BookFields.CREATED_YEAR, bookDto.getCreatedYear(),
                BookFields.AUTHOR_ID, bookDto.getAuthorId(),
                BookFields.GENRE_ID, bookDto.getGenreId()
        );
    }
}
