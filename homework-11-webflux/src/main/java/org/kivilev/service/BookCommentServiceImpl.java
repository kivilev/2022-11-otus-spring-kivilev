package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.config.LibraryProperties;
import org.kivilev.dao.repository.BookCommentRepository;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Book;
import org.kivilev.model.BookComment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookCommentServiceImpl implements BookCommentService {
    private final Clock clock;

    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;
    private final LibraryProperties libraryProperties;

    @Override
    @Transactional
    public void addComment(Pair<String, String> newData) {
        var bookId = newData.getLeft();
        var bookOptional = bookRepository.findById(bookId);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        var text = newData.getRight();

        var bookComment = new BookComment(text, LocalDateTime.now(clock).truncatedTo(ChronoUnit.MILLIS), book.getId());
        bookCommentRepository.save(bookComment);

        var newComments = getTopComments(book);
        book.setLastTopComments(newComments);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void removeComment(String removeId) {
        var bookCommentOptional = bookCommentRepository.findById(removeId);
        var bookComment = bookCommentOptional.orElseThrow(ObjectNotFoundException::new);
        bookCommentRepository.delete(bookComment);

        var bookOptional = bookRepository.findById(bookComment.getBookId());
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);

        var newComments = getTopComments(book);
        book.setLastTopComments(newComments);
        bookRepository.save(book);
    }

    @Override
    public List<BookComment> getAllComments(String bookId) {
        return bookCommentRepository.findAllByBookId(bookId);
    }

    private List<BookComment> getTopComments(Book book) {
        return bookCommentRepository.findAllByBookId(
                book.getId(),
                PageRequest.of(0, libraryProperties.getCountTopComments(), Sort.by("createDateTime").descending())
        );
    }
}
