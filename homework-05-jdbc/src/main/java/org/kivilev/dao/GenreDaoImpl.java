package org.kivilev.dao;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private static final String GET_ALL_GENRES = "select id, name from genre";
    private static final String GET_GENRE_BY_ID = "select id, name from genre where id = :id";
    private static final RowMapper<Genre> GENRE_ROW_MAPPER = (resultSet, i) -> {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    };
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Genre> getAllGenres() {
        return namedParameterJdbcOperations.query(GET_ALL_GENRES, GENRE_ROW_MAPPER);
    }

    @Override
    public Optional<Genre> getGenre(Long id) {
        return DaoUtils.wrapQueryForObject(namedParameterJdbcOperations, GET_GENRE_BY_ID, Map.of("id", id), GENRE_ROW_MAPPER);
    }
}
