package org.kivilev.service.io.input;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.ui.model.BookFields;
import org.kivilev.service.io.IoStreamService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class InputBookServiceImpl implements InputBookService {

    private final IoStreamService ioStreamService;

    @Override
    public Map<BookFields, Object> getNewBook() {
        ioStreamService.println("~~Создание новой книги~~");
        var title = ioStreamService.inputStringWithLabel("Название книги: ");
        var year = ioStreamService.inputStringWithLabel("Год создания: ");
        var authorId = ioStreamService.inputStringWithLabel("Id автора: ");
        var genreId = ioStreamService.inputStringWithLabel("Id жанра: ");
        return Map.of(BookFields.TITLE, title, BookFields.CREATED_YEAR, year, BookFields.AUTHOR_ID, authorId, BookFields.GENRE_ID, genreId);
    }

    @Override
    public String getRemoveId() {
        ioStreamService.println("~~Удаление книги~~");
        return ioStreamService.inputStringWithLabel("Id книги: ");
    }

    @Override
    public Pair<String, String> getNewTitle() {
        ioStreamService.println("~~Изменение заголовка книги~~");
        var id = ioStreamService.inputStringWithLabel("Id книги: ");
        var title = ioStreamService.inputStringWithLabel("Новое название книги: ");
        return Pair.of(id, title);
    }

    @Override
    public String getBookId() {
        ioStreamService.println("~~Информация по одной книге~~");
        return ioStreamService.inputStringWithLabel("Введите id книги: ");
    }

    @Override
    public String getCommentsBookId() {
        ioStreamService.println("~~Все комментарии книги~~");
        return ioStreamService.inputStringWithLabel("Id книги: ");
    }
}