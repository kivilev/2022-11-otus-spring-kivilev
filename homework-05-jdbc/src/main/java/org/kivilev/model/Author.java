package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Author {

    private Long id;
    private String name;
    private LocalDate birthDay;
    private LocalDate deathDay;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(Long id) {
        this.id = id;
    }
}
