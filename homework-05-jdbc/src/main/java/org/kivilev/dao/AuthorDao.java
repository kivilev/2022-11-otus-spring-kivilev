package org.kivilev.dao;

import org.kivilev.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> getAllAuthors();

    Optional<Author> getAuthor(Long id);
}
