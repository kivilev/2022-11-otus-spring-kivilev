package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.dao.repository.BookRepository;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.Genre;
import org.kivilev.service.model.BookFields;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final GenreService genreService;
    private final AuthorService authorService;

    private final BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book addBook(Map<BookFields, Object> bookFields) {

        Author author = getAuthor(bookFields);
        Genre genre = getGenre(bookFields);

        Book book = new Book(null,
                (String) bookFields.get(BookFields.TITLE),
                (Integer) bookFields.get(BookFields.CREATED_YEAR),
                author,
                genre,
                Collections.emptyList()
        );

        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void removeBook(long removeBookId) {
        bookRepository.deleteById(removeBookId);
    }

    @Override
    @Transactional
    public void updateBookTitle(Pair<Long, String> updateData) {
        var id = updateData.getLeft();
        var title = updateData.getRight();

        var bookOptional = bookRepository.findById(id);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        book.setTitle(title);

        bookRepository.save(book);
    }

    @Override
    public Book changeBook(Map<BookFields, Object> bookFields) {
        var bookOptional = bookRepository.findById((Long) bookFields.get(BookFields.ID));
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);

        book.setTitle((String) bookFields.get(BookFields.TITLE));
        book.setCreatedYear((Integer) bookFields.get(BookFields.CREATED_YEAR));
        book.setAuthor(getAuthor(bookFields));
        book.setGenre(getGenre(bookFields));

        return bookRepository.save(book);
    }

    @Override
    public Book getBook(long id) {
        var bookOptional = bookRepository.findById(id);
        return bookOptional.orElseThrow(ObjectNotFoundException::new);
    }

    private Author getAuthor(Map<BookFields, Object> bookFields) {
        return authorService.getAuthor((Long) bookFields.get(BookFields.AUTHOR_ID));
    }

    private Genre getGenre(Map<BookFields, Object> bookFields) {
        return genreService.getGenre((Long) bookFields.get(BookFields.GENRE_ID));
    }
}
