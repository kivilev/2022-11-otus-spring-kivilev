package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
    private static final String GET_ALL_AUTHORS = "select id, name, birthday, deathday from author";
    private static final String GET_AUTHOR_BY_ID = "select id, name, birthday, deathday from author where id = :id";
    private static final RowMapper<Author> AUTHOR_ROW_MAPPER = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();

        var resultDeathday = resultSet.getDate("deathday");
        LocalDate deathday = resultDeathday != null ? resultDeathday.toLocalDate() : null;

        return new Author(id, name, birthday, deathday);
    };

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Author> getAllAuthors() {
        return namedParameterJdbcOperations.query(GET_ALL_AUTHORS, Collections.emptyMap(), AUTHOR_ROW_MAPPER);
    }

    @Override
    public Optional<Author> getAuthor(Long id) {
        return DaoUtils.wrapQueryForObject(namedParameterJdbcOperations, GET_AUTHOR_BY_ID, Map.of("id", id), AUTHOR_ROW_MAPPER);
    }
}
