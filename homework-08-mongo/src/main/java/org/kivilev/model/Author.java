package org.kivilev.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "author")
public class Author {
    @Id
    private String id;
    private String name;
    private LocalDate birthday;
    private LocalDate deathday;

    public Author(String name, LocalDate birthday, LocalDate deathday) {
        this.name = name;
        this.birthday = birthday;
        this.deathday = deathday;
    }
}
