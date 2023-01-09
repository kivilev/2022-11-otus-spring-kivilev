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
}
