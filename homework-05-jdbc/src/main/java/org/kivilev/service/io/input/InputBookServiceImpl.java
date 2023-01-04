package org.kivilev.service.io.input;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.model.BookFields;
import org.kivilev.service.io.IoStreamService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class InputBookServiceImpl implements InputBookService {

    private final IoStreamService ioStreamService;

    @Override
    public Map<BookFields, Object> getNewBook() {
        ioStreamService.println("~~Создание новой книги~~");
        var title = ioStreamService.inputStringWithLabel("Название книги: ");
        var year = ioStreamService.inputIntWithLabel("Год создания: ");
        var authorId = ioStreamService.inputLongWithLabel("Id автора: ");
        var genreId = ioStreamService.inputLongWithLabel("Id жанра: ");
        return Map.of(BookFields.TITLE, title, BookFields.CREATED_YEAR, year, BookFields.AUTHOR_ID, authorId, BookFields.GENRE_ID, genreId);
    }

    @Override
    public long getRemoveId() {
        ioStreamService.println("~~Удаление книги~~");
        return ioStreamService.inputLongWithLabel("Id книги: ");
    }

    @Override
    public Pair<Long, String> getNewTitle() {
        ioStreamService.println("~~Изменение заголовка книги~~");
        var id = ioStreamService.inputLongWithLabel("Id книги: ");
        var title = ioStreamService.inputStringWithLabel("Новое название книги: ");
        return Pair.of(id, title);
    }
}