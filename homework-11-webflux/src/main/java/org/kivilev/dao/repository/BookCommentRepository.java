/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.dao.repository;

import org.kivilev.model.BookComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookCommentRepository extends ReactiveMongoRepository<BookComment, String> {
    Flux<BookComment> findAllByBookId(String bookId, PageRequest pageRequest);

    Flux<BookComment> findAllByBookId(String bookId);

}
