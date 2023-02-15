package org.kivilev.dao.repository;

import org.kivilev.model.BookComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
}
