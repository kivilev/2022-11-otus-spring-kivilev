/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.dao.repository;

import org.kivilev.model.BookComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {
    List<BookComment> findAllByBookId(String bookId, PageRequest pageRequest);

    List<BookComment> findAllByBookId(String bookId);

}
