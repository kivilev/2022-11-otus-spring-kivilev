/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.mapper;

import org.kivilev.controller.dto.NewBookDtoRequest;
import org.kivilev.service.model.BookFields;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BookDtoMapper {

    public Map<BookFields, Object> toBookFields(NewBookDtoRequest bookDto) {
        return Map.of(
                BookFields.TITLE, bookDto.getTitle(),
                BookFields.CREATED_YEAR, bookDto.getCreatedYear(),
                BookFields.AUTHOR_ID, bookDto.getAuthorId(),
                BookFields.GENRE_ID, bookDto.getGenreId()
        );
    }
}
