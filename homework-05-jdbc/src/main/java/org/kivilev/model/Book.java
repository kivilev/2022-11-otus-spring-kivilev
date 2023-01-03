package org.kivilev.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private Integer year;
    private Author author;
    private Genre genre;
}