package org.kivilev.service;

import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.model.BookComment;

import java.util.List;

public interface BookCommentService {
    void addComment(Pair<String, String> newData);

    void removeComment(String removeId);

    List<BookComment> getAllComments(String bookId);
}
