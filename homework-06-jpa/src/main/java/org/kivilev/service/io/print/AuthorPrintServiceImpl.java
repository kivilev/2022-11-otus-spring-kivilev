package org.kivilev.service.io.print;

import lombok.RequiredArgsConstructor;
import org.kivilev.model.Author;
import org.kivilev.service.io.OutputStreamData;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.kivilev.service.io.print.PrintUtils.dateToString;

@Service
@RequiredArgsConstructor
public class AuthorPrintServiceImpl implements AuthorPrintService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final OutputStreamData outputStreamData;

    @Override
    public void print(List<Author> allAuthors) {
        allAuthors.forEach(this::print);
    }

    @Override
    public void print(Author author) {
        var birthday = dateToString(author.getBirthday(), DATE_TIME_FORMATTER);
        var deathday = dateToString(author.getDeathday(), DATE_TIME_FORMATTER);
        outputStreamData.format("[%d] %s (%s-%s)\n", author.getId(), author.getName(), birthday, deathday);
    }


}