package org.kivilev.service.io.print;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.BookComment;
import org.kivilev.service.io.OutputStreamData;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.kivilev.service.io.print.PrintUtils.dateTimeToString;

@Service
@RequiredArgsConstructor
public class BookCommentPrintServiceImpl implements BookCommentPrintService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy H:m:s");

    private final OutputStreamData outputStreamData;

    @Override
    public void print(List<BookComment> comments) {
        comments.forEach(this::print);
    }

    @Override
    public void print(BookComment comment) {
        var createDateTime = dateTimeToString(comment.getCreateDateTime(), DATE_TIME_FORMATTER);
        outputStreamData.format("[%s - %s] %s\n", comment.getId(), createDateTime, comment.getText());
    }
}
