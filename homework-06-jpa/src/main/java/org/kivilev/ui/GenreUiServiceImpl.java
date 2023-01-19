package org.kivilev.ui;

import lombok.RequiredArgsConstructor;
import org.kivilev.service.GenreService;
import org.kivilev.service.io.print.GenrePrintService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenreUiServiceImpl implements GenreUiService {

    private final GenreService genreService;
    private final GenrePrintService genrePrintService;

    @Override
    public void showAllGenres() {
        genrePrintService.print(genreService.getAllGenres());
    }
}
