package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.kivilev.dao.BookDao;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.BookFields;
import org.kivilev.model.Genre;
import org.kivilev.service.io.input.InputBookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final InputBookService inputBookService;

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks().stream()
                .peek(book -> book.setAuthor(authorService.getAuthor(book.getAuthor().getId())))
                .peek(book -> book.setGenre(genreService.getGenre(book.getGenre().getId())))
                .collect(Collectors.toList());
    }

    @Override
    public void addBook() {
        Map<BookFields, Object> bookFields = inputBookService.getNewBook();

        Author author = authorService.getAuthor((Long) bookFields.get(BookFields.AUTHOR_ID));
        Genre genre = genreService.getGenre((Long) bookFields.get(BookFields.GENRE_ID));

        Book book = new Book(null,
                (String) bookFields.get(BookFields.TITLE),
                (Integer) bookFields.get(BookFields.CREATED_YEAR),
                author,
                genre
        );
        bookDao.save(book);
    }

    @Override
    public void removeBook() {
        long removeBookId = inputBookService.getRemoveId();
        bookDao.delete(removeBookId);
    }

    @Override
    public void updateBookTitle() {
        Pair<Long, String> newBookTitle = inputBookService.getNewTitle();
        bookDao.updateTitle(newBookTitle.getLeft(), newBookTitle.getRight());
    }
}
