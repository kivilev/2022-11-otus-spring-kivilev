package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.dao.BookCommentDao;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.BookComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentDao bookCommentDao;
    private final Clock clock;

    @Override
    @Transactional
    public void addComment(Pair<Long, String> newData) {
        var bookId = newData.getLeft();
        var text = newData.getRight();
        var bookComment = new BookComment(null, bookId, text, LocalDateTime.now(clock));
        bookCommentDao.save(bookComment);
    }

    @Override
    @Transactional
    public void removeComment(Long removeId) {
        var bookCommentOptional = bookCommentDao.getComment(removeId);
        var bookComment = bookCommentOptional.orElseThrow(ObjectNotFoundException::new);
        bookCommentDao.delete(bookComment);
    }
}
