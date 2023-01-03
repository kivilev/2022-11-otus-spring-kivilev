package org.kivilev.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Author {
    private Long id;
    private String name;
    private LocalDate birthDay;
}
