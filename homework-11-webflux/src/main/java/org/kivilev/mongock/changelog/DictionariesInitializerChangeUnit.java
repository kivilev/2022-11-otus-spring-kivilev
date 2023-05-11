/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.mongock.changelog;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackBeforeExecution;
import io.mongock.api.annotations.RollbackExecution;
import io.mongock.driver.mongodb.reactive.util.MongoSubscriberSync;
import io.mongock.driver.mongodb.reactive.util.SubscriberSync;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.kivilev.model.Author;
import org.kivilev.model.Genre;

import java.time.LocalDate;
import java.util.List;

@ChangeUnit(id = "dictionaries-initializer", order = "1", author = "d.kivilev")
@Slf4j
public class DictionariesInitializerChangeUnit {
    private static final String GENRE_COLLECTION_NAME = "genre";
    private static final String AUTHOR_COLLECTION_NAME = "author";

    @BeforeExecution
    public void beforeExecution(MongoDatabase mongoDatabase) {
        SubscriberSync<Void> subscriber = new MongoSubscriberSync<>();
        mongoDatabase.createCollection(GENRE_COLLECTION_NAME).subscribe(subscriber);
        mongoDatabase.createCollection(AUTHOR_COLLECTION_NAME).subscribe(subscriber);
        subscriber.await();
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoDatabase mongoDatabase) {
        SubscriberSync<Void> subscriber = new MongoSubscriberSync<>();
        mongoDatabase.getCollection(GENRE_COLLECTION_NAME).drop().subscribe(subscriber);
        mongoDatabase.getCollection(AUTHOR_COLLECTION_NAME).drop().subscribe(subscriber);
        subscriber.await();
    }

    @Execution
    public void execution(MongoDatabase mongoDatabase) {
        final List<Author> authors = List.of(
                new Author(null, "Стивен Кинг", LocalDate.of(1962, 1, 1), null),
                new Author(null, "Лев Толстой", LocalDate.of(1800, 9, 2), LocalDate.of(1890, 10, 1)),
                new Author(null, "Александр Пушкин", LocalDate.of(1799, 1, 1), LocalDate.of(1826, 2, 3)),
                new Author(null, "Агата Кристи", LocalDate.of(1899, 2, 1), LocalDate.of(1968, 1, 1))
        );
        final List<Genre> genres = List.of(
                new Genre(null, "детектив"),
                new Genre(null, "ужасы"),
                new Genre(null, "драма"),
                new Genre(null, "исторический роман")
        );

        SubscriberSync<InsertManyResult> subscriberGenres = new MongoSubscriberSync<>();
        mongoDatabase.getCollection(GENRE_COLLECTION_NAME, Genre.class)
                .insertMany(genres)
                .subscribe(subscriberGenres);

        InsertManyResult resultGenres = subscriberGenres.getFirst();
        log.info("Execution wasAcknowledged: {}", resultGenres.wasAcknowledged());
        resultGenres.getInsertedIds()
                .forEach((key, value) -> log.info("update id[{}] : {}", key, value));

        SubscriberSync<InsertManyResult> subscriberAuthors = new MongoSubscriberSync<>();
        mongoDatabase.getCollection(AUTHOR_COLLECTION_NAME, Author.class)
                .insertMany(authors)
                .subscribe(subscriberAuthors);

        InsertManyResult resultAuthors = subscriberAuthors.getFirst();
        log.info("Execution wasAcknowledged: {}", resultAuthors.wasAcknowledged());
        resultAuthors.getInsertedIds()
                .forEach((key, value) -> log.info("update id[{}] : {}", key, value));
    }

    @RollbackExecution
    public void rollbackExecution(MongoDatabase mongoDatabase) {
        SubscriberSync<DeleteResult> subscriber = new MongoSubscriberSync<>();
        mongoDatabase
                .getCollection(GENRE_COLLECTION_NAME, Genre.class)
                .deleteMany(new Document())
                .subscribe(subscriber);
        DeleteResult result = subscriber.getFirst();
        log.info("Genre rollbackExecution wasAcknowledged: {}", result.wasAcknowledged());
        log.info("Genre rollbackExecution deleted count: {}", result.getDeletedCount());

        mongoDatabase
                .getCollection(AUTHOR_COLLECTION_NAME, Author.class)
                .deleteMany(new Document())
                .subscribe(subscriber);
        result = subscriber.getFirst();
        log.info("Author rollbackExecution wasAcknowledged: {}", result.wasAcknowledged());
        log.info("Author rollbackExecution deleted count: {}", result.getDeletedCount());
    }
}
