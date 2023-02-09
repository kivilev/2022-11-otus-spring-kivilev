package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.BookComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final Clock clock;

    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void addComment(Pair<Long, String> newData) {
        var bookId = newData.getLeft();
        var bookOptional = bookRepository.findById(bookId);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        var text = newData.getRight();
        var bookComment = new BookComment(null, text, LocalDateTime.now(clock));
        book.getComments().add(bookComment);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void removeComment(Long bookId, Long removeId) {
        var bookCommentOptional = bookCommentRepository.findById(removeId);
        var bookComment = bookCommentOptional.orElseThrow(ObjectNotFoundException::new);

        var bookOptional = bookRepository.findById(bookId);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        book.getComments().remove(bookComment);
        bookRepository.save(book);
    }
}
