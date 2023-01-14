package org.kivilev.service.io.print;

import org.kivilev.model.BookComment;

import java.util.List;

public interface BookCommentPrintService {
    void print(List<BookComment> comments);

    void print(BookComment comment);
}
