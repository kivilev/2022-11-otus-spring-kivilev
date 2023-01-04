package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Genre {
    private Long id;
    private String name;

    public Genre(Long id) {
        this.id = id;
    }
}
