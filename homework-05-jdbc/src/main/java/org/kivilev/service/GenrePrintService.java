package org.kivilev.service;

import org.kivilev.model.Genre;

import java.util.List;

public interface GenrePrintService {
    void printGenres(List<Genre> allGenres);
}
