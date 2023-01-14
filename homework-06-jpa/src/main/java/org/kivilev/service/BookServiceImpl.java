package org.kivilev.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.dao.BookDao;
import org.kivilev.exception.ObjectNotFoundException;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.BookFields;
import org.kivilev.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Override
    @Transactional
    public Book addBook(Map<BookFields, Object> bookFields) {

        Author author = authorService.getAuthor((Long) bookFields.get(BookFields.AUTHOR_ID));
        Genre genre = genreService.getGenre((Long) bookFields.get(BookFields.GENRE_ID));

        Book book = new Book(null,
                (String) bookFields.get(BookFields.TITLE),
                (Integer) bookFields.get(BookFields.CREATED_YEAR),
                author,
                genre,
                Collections.emptyList()
        );
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void removeBook(long removeBookId) {
        var bookOptional = bookDao.getBook(removeBookId);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        bookDao.delete(book);
    }

    @Override
    @Transactional
    public void updateBookTitle(Pair<Long, String> updateData) {
        var id = updateData.getLeft();
        var title = updateData.getRight();

        var bookOptional = bookDao.getBook(id);
        var book = bookOptional.orElseThrow(ObjectNotFoundException::new);
        book.setTitle(title);
        bookDao.save(book);
    }

    @Override
    public Book getBook(long id) {
        var bookOptional = bookDao.getBook(id);
        return bookOptional.orElseThrow(ObjectNotFoundException::new);
    }
}
