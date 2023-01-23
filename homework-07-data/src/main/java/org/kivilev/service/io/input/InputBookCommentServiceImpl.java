package org.kivilev.service.io.input;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.service.io.IoStreamService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InputBookCommentServiceImpl implements InputBookCommentService {

    private final IoStreamService ioStreamService;

    @Override
    public Pair<Long, String> getNewComment() {
        ioStreamService.println("~~Добавление комментария~~");
        var bookId = ioStreamService.inputLongWithLabel("Id книги: ");
        var text = ioStreamService.inputStringWithLabel("Текст комментария: ");
        return Pair.of(bookId, text);
    }

    @Override
    public long getRemoveId() {
        ioStreamService.println("~~Удаление комментария~~");
        return ioStreamService.inputLongWithLabel("Id комментария: ");
    }
}
