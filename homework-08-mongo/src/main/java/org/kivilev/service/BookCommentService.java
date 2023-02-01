package org.kivilev.service;

import org.apache.commons.lang3.tuple.Pair;

public interface BookCommentService {
    void addComment(Pair<String, String> newData);

    void removeComment(String removeId);
}
