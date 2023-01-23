package org.kivilev.service;

import org.apache.commons.lang3.tuple.Pair;

public interface BookCommentService {
    void addComment(Pair<Long, String> newData);

    void removeComment(Long removeId);
}
