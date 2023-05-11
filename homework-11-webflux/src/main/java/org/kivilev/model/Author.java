package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "author")
@AllArgsConstructor
@ToString
public class Author {
    @Id
    private String id;
    private String name;
    private LocalDate birthday;
    private LocalDate deathday;
}
