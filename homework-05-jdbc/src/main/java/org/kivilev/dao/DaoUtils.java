package org.kivilev.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class DaoUtils {
    public static <T> Optional<T> wrapQueryForObject(NamedParameterJdbcOperations namedParameterJdbcOperations,
                                                     String sql,
                                                     Map<String, ?> paramMap,
                                                     RowMapper<T> rowMapper) {
        try {
            return Optional.ofNullable(
                    namedParameterJdbcOperations.queryForObject(sql, paramMap, rowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public static LocalDate getNullLocalDate(ResultSet resultSet, String fieldName) throws SQLException {
        var resultDeathday = resultSet.getDate(fieldName);
        return resultDeathday != null ? resultDeathday.toLocalDate() : null;
    }
}
