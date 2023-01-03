package org.kivilev.service;

import org.kivilev.model.Author;

import java.util.List;

public interface AuthorPrintService {
    void printAuthors(List<Author> allAuthors);
}
