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
import java.util.Objects;
import java.util.stream.Collectors;

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
    public void removeComment(Long removeId) {
        var bookCommentOptional = bookCommentRepository.findById(removeId);
        var bookComment = bookCommentOptional.orElseThrow(ObjectNotFoundException::new);

        var bookOptional = bookRepository.findById(bookComment.getId());
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        var actualComments = book.getComments().stream().filter(comment -> !Objects.equals(comment.getId(), removeId)).collect(Collectors.toList());
        book.setComments(actualComments);
        bookRepository.save(book);
    }
}
