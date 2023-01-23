package org.kivilev.service.io.print;

import org.kivilev.model.Genre;

import java.util.List;

public interface GenrePrintService {
    void print(List<Genre> genres);

    void print(Genre genre);
}
