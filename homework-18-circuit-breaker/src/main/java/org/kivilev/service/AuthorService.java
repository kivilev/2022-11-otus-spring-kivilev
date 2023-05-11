package org.kivilev.service;

import org.kivilev.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();

    Author getAuthor(Long id);
}
