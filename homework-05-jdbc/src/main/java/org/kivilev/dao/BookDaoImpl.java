package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Author;
import org.kivilev.model.Book;
import org.kivilev.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.kivilev.dao.DaoUtils.getNullLocalDate;

@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

    private static final String GET_ALL_BOOKS =
            "select b.id, b.title, b.created_year, " +
                    " g.id genre_id, g.name genre_name, " +
                    " a.id author_id, a.name author_name, a.birthday author_birthday, a.deathday author_deathday\n" +
                    " from book b " +
                    " left join genre g on g.id = b.genre_id " +
                    " left join author a on a.id = author_id";
    private static final String GET_BOOK_BY_ID =
            "select b.id, b.title, b.created_year, " +
                    " g.id genre_id, g.name genre_name, " +
                    " a.id author_id, a.name author_name, a.birthday author_birthday, a.deathday author_deathday\n" +
                    " from book b \n" +
                    " left join genre g on g.id = b.genre_id \n" +
                    " left join author a on a.id = author_id \n" +
                    "where b.id = :id";

    private static final String INSERT_ROW = "insert into book(title, created_year, author_id, genre_id) values (:title, :created_year, :author_id, :genre_id)";
    private static final String UPDATE_TITLE = "update book set title = :title where id = :id";
    private static final String DELETE_BOOK = "delete from book where id = :id";
    private static final RowMapper<Book> BOOK_ROW_MAPPER = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        int createdYear = resultSet.getInt("created_year");

        long genreId = resultSet.getLong("genre_id");
        String genreName = resultSet.getString("genre_name");

        long authorId = resultSet.getLong("author_id");
        String authorName = resultSet.getString("author_name");
        LocalDate authorBirthday = resultSet.getDate("author_birthday").toLocalDate();
        LocalDate authorDeathday = getNullLocalDate(resultSet, "author_deathday");

        return new Book(id, title, createdYear,
                new Author(authorId, authorName, authorBirthday, authorDeathday),
                new Genre(genreId, genreName)
        );
    };

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Book> getAllBooks() {
        return namedParameterJdbcOperations.query(GET_ALL_BOOKS, BOOK_ROW_MAPPER);
    }

    @Override
    public void save(Book book) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", book.getId())
                .addValue("title", book.getTitle())
                .addValue("created_year", book.getCreatedYear())
                .addValue("author_id", book.getAuthor().getId())
                .addValue("genre_id", book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(INSERT_ROW,
                parameters,
                keyHolder
        );
        var key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.longValue());
        }
    }

    @Override
    public void delete(long id) {
        namedParameterJdbcOperations.update(DELETE_BOOK, Map.of("id", id));
    }

    @Override
    public void updateTitle(long id, String title) {
        namedParameterJdbcOperations.update(UPDATE_TITLE, Map.of("id", id, "title", title));
    }

    @Override
    public Optional<Book> getBook(long id) {
        return DaoUtils.wrapQueryForObject(namedParameterJdbcOperations, GET_BOOK_BY_ID, Map.of("id", id), BOOK_ROW_MAPPER);
    }

}
