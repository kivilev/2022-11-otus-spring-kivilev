package org.kivilev.service.io.input;

import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.ui.model.BookFields;

import java.util.Map;

public interface InputBookService {
    Map<BookFields, Object> getNewBook();

    long getRemoveId();

    Pair<Long, String> getNewTitle();

    long getBookId();

    long getCommentsBookId();
}
