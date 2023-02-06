package org.kivilev.dao.repository;

import org.kivilev.model.BookComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookCommentRepository extends MongoRepository<BookComment, String> {
    List<BookComment> findAllByBook_Id(String bookId, PageRequest pageRequest);

    List<BookComment> findAllByBook_Id(String bookId);

}
