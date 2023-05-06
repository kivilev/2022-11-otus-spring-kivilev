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

        Author author = authorService.getAuthor((String) bookFields.get(BookFields.AUTHOR_ID));
        Genre genre = genreService.getGenre((String) bookFields.get(BookFields.GENRE_ID));

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
    public void removeBook(String removeBookId) {
        bookRepository.deleteById(removeBookId);
    }

    @Override
    @Transactional
    public void updateBookTitle(Pair<String, String> updateData) {
        var id = updateData.getLeft();
        var title = updateData.getRight();

        var bookOptional = bookRepository.findById(id);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        book.setTitle(title);

        bookRepository.save(book);
    }

    @Override
    public Book getBook(String id) {
        var bookOptional = bookRepository.findById(id);
        return bookOptional.orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public Book changeBook(Map<BookFields, Object> bookFields) {
        var bookOptional = bookRepository.findById((String) bookFields.get(BookFields.ID));
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);

        Author author = authorService.getAuthor((String) bookFields.get(BookFields.AUTHOR_ID));
        Genre genre = genreService.getGenre((String) bookFields.get(BookFields.GENRE_ID));

        book.setTitle((String) bookFields.get(BookFields.TITLE));
        book.setCreatedYear((Integer) bookFields.get(BookFields.CREATED_YEAR));
        book.setAuthor(author);
        book.setGenre(genre);

        return bookRepository.save(book);
    }
}
