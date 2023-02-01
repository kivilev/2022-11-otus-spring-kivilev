package org.kivilev.dao.repository;

import org.kivilev.model.BookComment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {
}
