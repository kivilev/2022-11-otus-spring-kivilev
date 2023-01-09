package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private Integer createdYear;
    private Author author;
    private Genre genre;
}