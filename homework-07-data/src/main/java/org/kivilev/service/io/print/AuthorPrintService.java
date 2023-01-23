package org.kivilev.service.io.print;

import org.kivilev.model.Author;

import java.util.List;

public interface AuthorPrintService {
    void print(List<Author> allAuthors);

    void print(Author author);
}
