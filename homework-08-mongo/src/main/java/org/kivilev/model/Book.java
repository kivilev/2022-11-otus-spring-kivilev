package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "book")
public class Book {
    @Id
    private String id;
    private String title;
    private Integer createdYear;
    private Author author;
    private Genre genre;
    private List<BookComment> comments;
}