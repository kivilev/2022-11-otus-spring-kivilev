package org.kivilev.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.kivilev.dao.repository.AuthorRepository;
import org.kivilev.dao.repository.GenreRepository;
import org.kivilev.model.Author;
import org.kivilev.model.Genre;

import java.time.LocalDate;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "d.kivilev", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "fillDictionaries", author = "d.kivilev")
    public void fillDictionaries(AuthorRepository authorRepository, GenreRepository genreRepository) {
        List<Author> authors = List.of(
                new Author("1", "Стивен Кинг", LocalDate.of(1962, 1, 1), null),
                new Author("2", "Лев Толстой", LocalDate.of(1800, 9, 2), LocalDate.of(1890, 10, 1)),
                new Author("3", "Александр Пушкин", LocalDate.of(1799, 1, 1), LocalDate.of(1826, 2, 3)),
                new Author("4", "Агата Кристи", LocalDate.of(1899, 2, 1), LocalDate.of(1968, 1, 1))
        );

        authorRepository.saveAll(authors);

        List<Genre> genres = List.of(
                new Genre("1", "детектив"),
                new Genre("2", "ужасы"),
                new Genre("3", "драма"),
                new Genre("4", "исторический роман")
        );
        genreRepository.saveAll(genres);
    }
}
