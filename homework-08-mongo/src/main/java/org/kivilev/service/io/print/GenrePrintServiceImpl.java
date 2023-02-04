package org.kivilev.service.io.print;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Genre;
import org.kivilev.service.io.OutputStreamData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenrePrintServiceImpl implements GenrePrintService {
    private final OutputStreamData outputStreamData;

    @Override
    public void print(List<Genre> genres) {
        genres.forEach(this::print);
    }

    @Override
    public void print(Genre genre) {
        outputStreamData.format("[%s] %s\n", genre.getId(), genre.getName());
    }
}
